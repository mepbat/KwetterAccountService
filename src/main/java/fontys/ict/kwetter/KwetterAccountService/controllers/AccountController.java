package fontys.ict.kwetter.KwetterAccountService.controllers;

import fontys.ict.kwetter.KwetterAccountService.models.Account;
import fontys.ict.kwetter.KwetterAccountService.repositories.AccountRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountRepository accountRepository;
    private final AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingKey}")
    private String routingkey;

    public AccountController(AccountRepository accountRepository, AmqpTemplate rabbitTemplate) {
        this.accountRepository = accountRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    Optional<Account> getAccount(@PathVariable("accountId") Long accountId) {
        return accountRepository.findById(accountId);
    }

    @RequestMapping(value = "/getAccountByUsername/{username}", method = RequestMethod.GET)
    public @ResponseBody
    Optional<Account> getAccountByUsername(@PathVariable("username") String username) {
        return accountRepository.getAccountByUsername(username);
    }

    @RequestMapping(value = "/search/{search}", method = RequestMethod.GET)
    public @ResponseBody
    List<Account> getAccountsFromSearch(@PathVariable("search") String search) {
        return accountRepository.getAccountsByUsernameContains(search);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<Account> getAll() {
        return accountRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody Account createAccount(@RequestBody Account account){
        return accountRepository.save(account);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public @ResponseBody Account updateAccount(@RequestBody Account account){
        return accountRepository.save(account);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public @ResponseBody void deleteAccount(@RequestBody Account account){
        accountRepository.delete(account);
    }
}
