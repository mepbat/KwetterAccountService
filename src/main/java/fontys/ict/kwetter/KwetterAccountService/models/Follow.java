package fontys.ict.kwetter.KwetterAccountService.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "following_account_id", nullable = false)
    private Account followingAccount;

    @PreRemove
    private void removeChildren(){
        this.account.setFollowers(null);
        this.account.setFollowing(null);
        this.followingAccount.setFollowers(null);
        this.followingAccount.setFollowing(null);

    }

    public Follow() {
    }

    public Follow(Long id, Account account, Account followingAccount) {
        this.id = id;
        this.account = account;
        this.followingAccount = followingAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getFollowingAccount() {
        return followingAccount;
    }

    public void setFollowingAccount(Account followingAccount) {
        this.followingAccount = followingAccount;
    }
}
