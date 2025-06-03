package uni.project.online.shop.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.project.online.shop.model.Product;
import uni.project.online.shop.repository.FavoritesRepository;

import java.util.List;

@Service
public class FavoritesService {
    @Autowired
    FavoritesRepository favoritesRepository;

    public void addFavorite(Long productId, Long userId) {
        if (favoritesRepository.favorite(productId, userId) != null)
            return;

        favoritesRepository.addFavorite(productId, userId);
    }

    public List<Product> getFavorites(Long userId) {
        return favoritesRepository.getFavorites(userId);
    }

    public void removeFavorite(Long productId, Long userId) {
        favoritesRepository.removeFavorite(productId, userId);
    }
}
