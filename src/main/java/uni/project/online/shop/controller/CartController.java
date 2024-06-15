package uni.project.online.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import uni.project.online.shop.api.CartApi;
import uni.project.online.shop.model.CartProduct;
import uni.project.online.shop.model.order.ItemToAdd;
import uni.project.online.shop.service.AuthService;
import uni.project.online.shop.service.CartService;

import java.util.List;

@RestController
public class CartController implements CartApi {
    @Autowired
    CartService cartService;
    @Autowired
    AuthService authService;

    @Override
    public int getItemsInCartCount(HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        return cartService.getCartItemsCount(userId);
    }

    @Override
    public void addItem(ItemToAdd item, HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        cartService.addItem(item, userId);
    }

    @Override
    public List<CartProduct> getItemsInCart(HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        return cartService.getItemsInCart(userId);
    }

    @Override
    public void deleteItemInCart(Long id, HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        cartService.deleteItemInCart(id, userId);
    }

    @Override
    public void editQuantity(ItemToAdd item, HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        cartService.editQuantity(item, userId);
    }
}
