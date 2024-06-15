package uni.project.online.shop.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import uni.project.online.shop.model.Product;

import java.util.List;

@RequestMapping("/favorites")
public interface FavouritesApi {
    @PostMapping("/add")
    public void addFavoriteItem(@RequestParam("id") Long id, HttpSession session);

    @GetMapping()
    public List<Product> getFavoriteItems(HttpSession session);

    @DeleteMapping("/remove")
    public void removeFavorite(@RequestParam("id") Long id, HttpSession session);
}
