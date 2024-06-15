package uni.project.online.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import uni.project.online.shop.api.UserApi;
import uni.project.online.shop.model.PurchaseHistory;
import uni.project.online.shop.model.User;
import uni.project.online.shop.service.AuthService;
import uni.project.online.shop.service.UserService;

import java.util.List;

@RestController
public class UserController implements UserApi {
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;
    @Override
    public User getUser(HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        return userService.getUser(userId);
    }

    @Override
    public User editUser(User user, HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        return userService.editUser(user, userId);
    }

    @Override
    public List<PurchaseHistory> getUserPurchases(HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        return userService.getUserPurchases(userId);
    }
}
