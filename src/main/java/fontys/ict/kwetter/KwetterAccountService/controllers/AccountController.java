package fontys.ict.kwetter.KwetterAccountService.controllers;

import fontys.ict.kwetter.KwetterAccountService.models.Account;
import fontys.ict.kwetter.KwetterAccountService.repositories.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8081","http://localhost:8082","http://localhost:8083"})
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    Optional<Account> getAccount(@PathVariable("accountId") Long accountId) {
        return accountRepository.findById(accountId);
    }

    @RequestMapping(value = "/search/{search}", method = RequestMethod.GET)
    public @ResponseBody
    List<Account> getAccountsFromSearch(@PathVariable("search") String search) {
        return accountRepository.getAccountsByNameContains(search);
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
