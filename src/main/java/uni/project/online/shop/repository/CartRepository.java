package uni.project.online.shop.repository;

import org.apache.ibatis.annotations.*;
import uni.project.online.shop.model.CartProduct;
import uni.project.online.shop.model.Product;
import uni.project.online.shop.model.order.ItemToAdd;

import java.util.List;

@Mapper
public interface CartRepository {

    @Select("SELECT COUNT(*) FROM cart WHERE user_id = #{userId}")
    int getCartItemsCount(Long userId);

    @Insert("INSERT INTO cart(product_id, user_id, quantity) " +
            "VALUES(#{item.productId}, #{userId}, #{item.quantity})")
    void addItem(ItemToAdd item, Long userId);

    @Select("SELECT p.*, t.name AS type, c.name AS category, b.name AS brand, tg.name AS targetGroup, cart.quantity AS cartQuantity " +
            "FROM cart " +
            "JOIN product p ON cart.product_id = p.id " +
            "LEFT JOIN type t ON p.type_id = t.id " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "LEFT JOIN brand b ON p.brand_id = p.id " +
            "LEFT JOIN target_group tg ON p.target_group_id = tg.id " +
            "WHERE cart.user_id = #{userId}")
    List<CartProduct> getItemsInCard(Long userId);

    @Delete("DELETE FROM cart WHERE user_id = #{userId} AND product_id = #{productId}")
    void deleteItem(Long productId, Long userId);

    @Update("UPDATE cart SET quantity = #{item.quantity} " +
            "WHERE user_id = #{userId} AND product_id = #{item.productId}")
    void editQuantity(ItemToAdd item, Long userId);

    @Select("SELECT quantity FROM cart WHERE user_id = #{userId} AND product_id = #{productId}")
    Integer checkIfAdded(Long productId, Long userId);
}
