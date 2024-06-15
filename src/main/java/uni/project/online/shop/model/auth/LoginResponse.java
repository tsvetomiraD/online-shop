package uni.project.online.shop.model.auth;

import uni.project.online.shop.model.User;

public class LoginResponse {
    private String token;
    private User user;

    public LoginResponse() {
    }

    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
