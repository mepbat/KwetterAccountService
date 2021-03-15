package fontys.ict.kwetter.KwetterAccountService.repositories;

import fontys.ict.kwetter.KwetterAccountService.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> getAccountsByNameContains(String search);
}
