package fontys.ict.kwetter.KwetterAccountService.repositories;

import fontys.ict.kwetter.KwetterAccountService.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> getAccountsByUsernameContains(String search);
    Optional<Account> getAccountByUsername(String name);
}
