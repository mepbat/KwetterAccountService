package fontys.ict.kwetter.KwetterAccountService.controllers;

import fontys.ict.kwetter.KwetterAccountService.models.Account;
import fontys.ict.kwetter.KwetterAccountService.models.Role;
import fontys.ict.kwetter.KwetterAccountService.repositories.AccountRepository;
import fontys.ict.kwetter.KwetterAccountService.repositories.RoleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    public AccountController(AccountRepository accountRepository, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    Optional<Account> getAccount(@PathVariable("accountId") Long accountId) {
        return accountRepository.findById(accountId);
    }

    @RequestMapping(value = "/{search}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    Role getAccountRole(@PathVariable("accountId") Long accountId) {
        return roleRepository.getRoleByAccount_Id(accountId);
    }
}
