package fontys.ict.kwetter.KwetterAccountService.controllers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fontys.ict.kwetter.KwetterAccountService.models.Account;
import fontys.ict.kwetter.KwetterAccountService.models.Follow;
import fontys.ict.kwetter.KwetterAccountService.models.FollowDto;
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

    @RequestMapping(value = "/followers/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Follow> getFollowers(@PathVariable("accountId") Long accountId) {
        return followRepository.getAllByFollowingAccount_Id(accountId);
    }

    @RequestMapping(value = "/following/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Follow> getFollowing(@PathVariable("accountId") Long accountId) {
        return followRepository.getAllByAccount_Id(accountId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> follow(@RequestBody FollowDto followdto) {
        System.out.println(followdto);
        Follow follow = new Follow();
        Optional<Account> account = accountRepository.findAccountById((long) followdto.getAccountId());
        if(account.isEmpty()){
            return new ResponseEntity<>("Account not found", HttpStatus.BAD_REQUEST);
        }
        Optional<Account> followAccount = accountRepository.findAccountById((long) followdto.getFollowingAccountId());
        if(followAccount.isEmpty()){
            return new ResponseEntity<>("Follow Account not found", HttpStatus.BAD_REQUEST);
        }
        follow.setAccount(account.get());
        follow.setFollowingAccount(followAccount.get());
        return new ResponseEntity<>(this.gson.toJson(followRepository.save(follow)), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> unfollow(@RequestBody FollowDto followdto) {
        Optional<Follow> follow = followRepository.findById((long) followdto.getId());
        if(follow.isEmpty()){
            return new ResponseEntity<>("Follow not found", HttpStatus.BAD_REQUEST);
        }
        followRepository.delete(follow.get());
        return new ResponseEntity<>("Unfollow succeeded", HttpStatus.OK);
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
