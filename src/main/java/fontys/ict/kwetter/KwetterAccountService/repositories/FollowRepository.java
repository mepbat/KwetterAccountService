package fontys.ict.kwetter.KwetterAccountService.repositories;

import fontys.ict.kwetter.KwetterAccountService.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> getAllByAccount_Id(Long accountId);
    List<Follow> getAllByFollowingAccount_Id(Long followingAccountId);
    Optional<Follow> findByAccountIdAndFollowingAccountId(Long accountId, Long followingAccountId);
    Optional<Follow> findById(Long id);
    boolean existsFollowByAccountIdAndFollowingAccountId(Long accountId, Long followingAccountId);
}
