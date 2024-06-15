package uni.project.online.shop.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import uni.project.online.shop.model.Product;

import java.util.List;

@Mapper
public interface FavoritesRepository {
    @Select("SELECT id FROM favorites WHERE user_id = #{userId} AND product_id = #{productId}")
    Long favorite(Long productId, Long userId);

    @Insert("INSERT INTO favorites(product_id, user_id) " +
            "VALUES(#{productId}, #{userId})")
    void addFavorite(Long productId, Long userId);
    @Select("SELECT p.*, t.name AS type, c.name AS category, b.name AS brand, tg.name AS targetGroup " +
            "FROM favorites f " +
            "JOIN product p ON f.product_id = p.id " +
            "LEFT JOIN type t ON p.type_id = t.id " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "LEFT JOIN brand b ON p.brand_id = p.id " +
            "LEFT JOIN target_group tg ON p.target_group_id = tg.id " +
            "WHERE f.user_id = #{userId}")
    List<Product> getFavorites(Long userId);

    @Delete("DELETE FROM favorites WHERE user_id = #{userId} AND product_id = #{productId}")
    void removeFavorite(Long productId, Long userId);
}
