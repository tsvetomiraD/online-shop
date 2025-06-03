package uni.project.online.shop.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import uni.project.online.shop.model.auth.LoginRequest;
import uni.project.online.shop.model.auth.LoginResponse;
import uni.project.online.shop.model.User;

@RequestMapping("/auth")
public interface AuthApi {
    @PostMapping("/login")
    public LoginResponse login(HttpSession session, @RequestBody LoginRequest loginRequest);

    @PostMapping("/logout")
    public void logout(HttpSession session);

    @PostMapping("/register")
    public void register(@RequestBody User user);

    @GetMapping("/register/complete")
    public boolean completeRegister(@RequestParam("token") String token);

    @PostMapping("/forgotten-password")
    public void forgottenPassword(@RequestBody String email);

    @PostMapping("/reset-forgotten-password")
    public boolean resetForgottenPassword(@RequestParam("token") String token, @RequestParam("password") String password);

    @PostMapping("/subscribe")
    public void sendPromoCode(@RequestParam("email") String email);
}
