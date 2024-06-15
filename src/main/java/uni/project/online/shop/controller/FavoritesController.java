package uni.project.online.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import uni.project.online.shop.api.FavouritesApi;
import uni.project.online.shop.model.Product;
import uni.project.online.shop.service.AuthService;
import uni.project.online.shop.service.FavoritesService;

import java.util.List;

@RestController
public class FavoritesController implements FavouritesApi {
    @Autowired
    FavoritesService favoritesService;
    @Autowired
    AuthService authService;

    @Override
    public void addFavoriteItem(Long id, HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        favoritesService.addFavorite(id, userId);
    }

    @Override
    public List<Product> getFavoriteItems(HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        return favoritesService.getFavorites(userId);
    }

    @Override
    public void removeFavorite(Long id, HttpSession session) {
        Long userId = authService.getUserIdFromSession(session);
        favoritesService.removeFavorite(id, userId);
    }
}
