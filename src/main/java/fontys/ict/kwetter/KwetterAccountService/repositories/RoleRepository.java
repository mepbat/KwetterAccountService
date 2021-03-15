package fontys.ict.kwetter.KwetterAccountService.repositories;

import fontys.ict.kwetter.KwetterAccountService.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role getRoleByAccount_Id(Long accountId);
}
