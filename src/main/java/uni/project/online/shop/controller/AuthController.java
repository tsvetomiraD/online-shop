package uni.project.online.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import uni.project.online.shop.api.AuthApi;
import uni.project.online.shop.model.auth.LoginRequest;
import uni.project.online.shop.model.auth.LoginResponse;
import uni.project.online.shop.model.User;
import uni.project.online.shop.service.AuthService;

@RestController
public class AuthController implements AuthApi {
    @Autowired
    AuthService authService;
    @Override
    public LoginResponse login(HttpSession session, LoginRequest loginRequest) {
        return authService.login(session, loginRequest);
    }

    @Override
    public void logout(HttpSession session) {
        authService.logout(session);
    }

    @Override
    public void register(User user)  {
        authService.register(user);
    }

    @Override
    public boolean completeRegister(String token)  {
        return authService.completeRegistration(token);
    }

    @Override
    public void forgottenPassword(String email) {
        authService.forgottenPassword(email);
    }

    @Override
    public boolean resetForgottenPassword(String token, String password) {
        return authService.resetForgottenPassword(token, password);
    }

    @Override
    public void sendPromoCode(String email) {
        authService.sendPromoCode(email);
    }
}
