package fontys.ict.kwetter.KwetterAccountService.models;

public class FollowDto {
    private int id;
    private int accountId;
    private int followingAccountId;

    public FollowDto() {
    }

    public FollowDto(int id, int accountId, int followingAccountId) {
        this.id = id;
        this.accountId = accountId;
        this.followingAccountId = followingAccountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getFollowingAccountId() {
        return followingAccountId;
    }

    public void setFollowingAccountId(int followingAccountId) {
        this.followingAccountId = followingAccountId;
    }

    @Override
    public String toString() {
        return "FollowDto{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", followingAccountId=" + followingAccountId +
                '}';
    }
}
