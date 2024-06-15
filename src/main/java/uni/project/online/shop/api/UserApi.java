package uni.project.online.shop.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import uni.project.online.shop.model.CartProduct;
import uni.project.online.shop.model.PurchaseHistory;
import uni.project.online.shop.model.User;
import uni.project.online.shop.model.order.ItemToAdd;

import java.util.List;

@RequestMapping("/user")
public interface UserApi {
    @GetMapping()
    public User getUser(HttpSession session);

    @PutMapping()
    public User editUser(@RequestBody User user, HttpSession session);

    @GetMapping("/purchases")
    public List<PurchaseHistory> getUserPurchases(HttpSession session);
}
