package fontys.ict.kwetter.KwetterAccountService.controllers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fontys.ict.kwetter.KwetterAccountService.models.Account;
import fontys.ict.kwetter.KwetterAccountService.models.Follow;
import fontys.ict.kwetter.KwetterAccountService.models.HibernateProxyTypeAdapter;
import fontys.ict.kwetter.KwetterAccountService.models.dto.AccountDto;
import fontys.ict.kwetter.KwetterAccountService.repositories.AccountRepository;
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
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountRepository accountRepository;
    private final AmqpTemplate rabbitTemplate;
    private final Gson gson;

    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingKey}")
    private String routingkey;

    public AccountController(AccountRepository accountRepository, AmqpTemplate rabbitTemplate) {
        this.accountRepository = accountRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.gson = initiateGson();
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAccount(@PathVariable("accountId") Long accountId) {
        Optional<Account> account = accountRepository.findAccountById(accountId);
        if (account.isEmpty()) {
            return new ResponseEntity<>("Account not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gson.toJson(account.get()), HttpStatus.OK);
    }

    @RequestMapping(value = "/getAccountByUsername/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getAccountByUsername(@PathVariable("username") String username) {
        Optional<Account> account = accountRepository.findAccountByUsername(username);
        if (account.isEmpty()) {
            return new ResponseEntity<>("Account not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gson.toJson(account.get()), HttpStatus.OK);
    }

    @RequestMapping(value = "/search/{search}", method = RequestMethod.GET)
    public ResponseEntity<?> getAccountsFromSearch(@PathVariable("search") String search) {
        return new ResponseEntity<>(gson.toJson(accountRepository.findTop10AccountsByUsernameContains(search)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(gson.toJson(accountRepository.findAll()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        return new ResponseEntity<>(gson.toJson(accountRepository.save(account)),HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateAccount(@RequestBody AccountDto accountDto) {
        Account account = new Account(accountDto);
        return new ResponseEntity<>(gson.toJson(accountRepository.save(account)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public @ResponseBody
    void deleteAccount(@RequestBody Account account) {
        accountRepository.delete(account);
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
                            if (f.getDeclaredClass() == Account.class) {
                                    exclude = true;
                            }

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

    }};
}
