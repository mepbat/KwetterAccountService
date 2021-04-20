package fontys.ict.kwetter.KwetterAccountService.controllers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;
import fontys.ict.kwetter.KwetterAccountService.models.Account;
import fontys.ict.kwetter.KwetterAccountService.models.Follow;
import fontys.ict.kwetter.KwetterAccountService.models.dto.FollowDto;
import fontys.ict.kwetter.KwetterAccountService.models.HibernateProxyTypeAdapter;
import fontys.ict.kwetter.KwetterAccountService.repositories.AccountRepository;
import fontys.ict.kwetter.KwetterAccountService.repositories.FollowRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/follow")
public class FollowController {
    private final FollowRepository followRepository;
    private final AccountRepository accountRepository;
    private final AmqpTemplate rabbitTemplate;
    private final Gson gson;

    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingKey}")
    private String routingkey;

    public FollowController(FollowRepository followRepository, AccountRepository accountRepository, AmqpTemplate rabbitTemplate) {
        this.followRepository = followRepository;
        this.accountRepository = accountRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.gson = initiateGson();
    }

    @RequestMapping(value = "/isFollowing/{accountId}/{followingAccountId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    boolean isFollowing(@PathVariable("accountId") Long accountId, @PathVariable("followingAccountId") Long followingAccountId) {
        return followRepository.existsFollowByAccountIdAndFollowingAccountId(accountId, followingAccountId);
    }

    @RequestMapping(value = "/followers/{accountId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getFollowers(@PathVariable("accountId") Long accountId) {
        String json = this.gson.toJson(followRepository.getAllByFollowingAccount_Id(accountId));
        List<Account> accounts = JsonPath.read(json, "$..account");
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @RequestMapping(value = "/following/{accountId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getFollowing(@PathVariable("accountId") Long accountId) {
        String json = this.gson.toJson(followRepository.getAllByAccount_Id(accountId));
        List<Account> accounts = JsonPath.read(json, "$..followingAccount");
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> follow(@RequestBody FollowDto followdto) {
        System.out.println(followdto);
        Follow follow = new Follow();
        if (followRepository.existsFollowByAccountIdAndFollowingAccountId((long) followdto.getAccountId(), (long) followdto.getFollowingAccountId())) {
            return new ResponseEntity<>("Already followed", HttpStatus.CONFLICT);
        }
        Optional<Account> account = accountRepository.findAccountById((long) followdto.getAccountId());
        if (account.isEmpty()) {
            return new ResponseEntity<>("Account not found", HttpStatus.BAD_REQUEST);
        }
        Optional<Account> followAccount = accountRepository.findAccountById((long) followdto.getFollowingAccountId());
        if (followAccount.isEmpty()) {
            return new ResponseEntity<>("Follow Account not found", HttpStatus.BAD_REQUEST);
        }
        follow.setAccount(account.get());
        follow.setFollowingAccount(followAccount.get());
        return new ResponseEntity<>(this.gson.toJson(followRepository.save(follow)), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/unfollow", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
    public ResponseEntity<?> unfollow(@RequestBody FollowDto followdto) {
        Optional<Follow> follow = followRepository.findByAccountIdAndFollowingAccountId((long) followdto.getAccountId(), (long) followdto.getFollowingAccountId());
        if (follow.isEmpty()) {
            return new ResponseEntity<>("Follow not found", HttpStatus.BAD_REQUEST);
        }
        try {
            followRepository.deleteById(follow.get().getId());
            return new ResponseEntity<>("Unfollow succeeded", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Could not unfollow" + ex, HttpStatus.BAD_REQUEST);
        }
    }

    private Gson initiateGson() {
        GsonBuilder b = new GsonBuilder();
        b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        boolean exclude = false;
                        try {
                            exclude = EXCLUDE.contains(f.getName());
                        } catch (Exception ignore) {
                        }
                        return exclude;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                });
        return b.create();
    }

    private static final List<String> EXCLUDE = new ArrayList<>() {{
        add("followers");
        add("following");
    }};
}
