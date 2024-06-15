package uni.project.online.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.project.online.shop.model.CartProduct;
import uni.project.online.shop.model.Product;
import uni.project.online.shop.model.order.ItemToAdd;
import uni.project.online.shop.repository.CartRepository;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    public int getCartItemsCount(Long userId) {
        return cartRepository.getCartItemsCount(userId);
    }

    public void addItem(ItemToAdd item,  Long userId) {
        Integer quantity = cartRepository.checkIfAdded(item.getProductId(), userId);
        if (quantity != null) {
            item.setQuantity(item.getQuantity() + quantity);
            cartRepository.editQuantity(item, userId);
            return;
        }
        cartRepository.addItem(item, userId);
    }

    public List<CartProduct> getItemsInCart(Long userId) {
        return cartRepository.getItemsInCard(userId);
    }

    public void deleteItemInCart(Long id, Long userId) {
        cartRepository.deleteItem(id, userId);
    }

    public void editQuantity(ItemToAdd item, Long userId) {
        cartRepository.editQuantity(item, userId);
    }
}
