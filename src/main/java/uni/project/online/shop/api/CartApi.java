package uni.project.online.shop.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import uni.project.online.shop.model.CartProduct;
import uni.project.online.shop.model.Product;
import uni.project.online.shop.model.order.ItemToAdd;

import java.util.List;

@RequestMapping("/cart")
public interface CartApi {
    @GetMapping("/count")
    public int getItemsInCartCount(HttpSession session);

    @PostMapping("/add")
    public void addItem(@RequestBody ItemToAdd item, HttpSession session);

    @GetMapping()
    public List<CartProduct> getItemsInCart(HttpSession session);

    @DeleteMapping("/delete")
    public void deleteItemInCart(@RequestParam("id") Long id, HttpSession session);

    @PutMapping("/edit-quantity")
    public void editQuantity(@RequestBody ItemToAdd item, HttpSession session);
}
