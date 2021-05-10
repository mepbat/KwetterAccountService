package fontys.ict.kwetter.KwetterAccountService.models.dto;

public class AccountDto {
    private Long id;
    private boolean active;
    private String username;
    private String photo;
    private String location;
    private String web;
    private String bio;

    public AccountDto() {
    }

    public AccountDto(Long id, boolean active, String username, String photo, String location, String web, String bio) {
        this.id = id;
        this.active = active;
        this.username = username;
        this.photo = photo;
        this.location = location;
        this.web = web;
        this.bio = bio;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
