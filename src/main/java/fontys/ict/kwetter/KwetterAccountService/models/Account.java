package fontys.ict.kwetter.KwetterAccountService.models;

import fontys.ict.kwetter.KwetterAccountService.models.dto.AccountDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    @Column
    private Long id;

    @Column
    private boolean active;

    @Column
    private String username;
    @Column
    private String photo;
    @Column
    private String bio;
    @Column
    private String location;
    @Column
    private String web;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Follow> following;

    @OneToMany(mappedBy = "followingAccount", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Follow> followers;

/*    @PreRemove
    private void removeChildren(){
        this.following = null;
        this.followers = null;
    }*/

    public Account() {
    }

    public Account(Long id, boolean active, String username, String photo, String bio, String location, String web, List<Follow> following, List<Follow> followers) {
        this.id = id;
        this.active = active;
        this.username = username;
        this.photo = photo;
        this.bio = bio;
        this.location = location;
        this.web = web;
        this.following = following;
        this.followers = followers;
    }

    public Account(AccountDto dto) {
        this.id = dto.getId();
        this.username = dto.getUsername();
        this.location = dto.getLocation();
        this.web = dto.getWeb();
        this.bio = dto.getBio();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public List<Follow> getFollowing() {
        return following;
    }

    public void setFollowing(List<Follow> following) {
        this.following = following;
    }

    public List<Follow> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Follow> followers) {
        this.followers = followers;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", photo='" + photo + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", web='" + web + '\'' +
                ", following=" + following +
                ", followers=" + followers +
                '}';
    }
}
