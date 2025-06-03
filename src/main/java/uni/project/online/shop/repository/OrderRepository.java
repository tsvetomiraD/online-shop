package uni.project.online.shop.repository;

import org.apache.ibatis.annotations.*;
import uni.project.online.shop.model.*;
import uni.project.online.shop.model.order.Code;
import uni.project.online.shop.model.order.DbOrder;
import uni.project.online.shop.model.order.ItemToAdd;
import uni.project.online.shop.model.order.Order;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderRepository {
    @Select("SELECT * FROM promo_code WHERE code = #{code}")
    Code checkCode(String code);

    @Select("SELECT percentage FROM promo_code " +
            "WHERE code = #{code} AND NOW() < expiration ")
    Integer checkCode1(String code);


    @Select("SELECT * FROM promo_code WHERE code = #{code} AND user_id = #{userId}")
    Code checkUserCode(String code, Long userId);

    @Insert("INSERT INTO `order`(user_id, email, first_name, last_name, phone, address, total_price, order_id, status, public_id, type) " +
            "VALUES(#{userId}, #{email}, #{firstName}, #{lastName}, #{phone}, #{address}, #{totalPrice}, #{orderId}, #{status}, #{publicId}, #{type})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void order(DbOrder order);

    @Delete("DELETE FROM cart WHERE product_id = #{productId} AND user_id = #{userId}")
    void deleteFromCart(Long productId, Long userId);

    @Update("UPDATE product SET quantity = quantity ${plusOrMinus} #{quantity} WHERE id = #{productId}")
    void editQuantity(Long productId, int quantity, @Param("plusOrMinus") String plusOrMinus);

    @Insert("INSERT INTO order_products(product_id, quantity, order_id) " +
            "VALUES(#{productId}, #{quantity}, #{orderId})")
    void addOrderProduct(Long productId, int quantity, Long orderId);

    @Select("SELECT * FROM order " +
            "WHERE id = #{id}")
    Order getOrder(Long id);

    @Select("SELECT * FROM order_products " +
            "WHERE order_id = #{id}")
    List<ItemToAdd> getOrderItems(Long id);

    @Update("UPDATE order SET processed = 1 WHERE id = #{id}")
    void processOrder(Long id);

    @Delete("DELETE FROM order WHERE id = #{id}")
    void deleteOrder(Long id);
    @Delete("DELETE FROM order_products WHERE order_id = #{id}")
    void deleteOrderItems(Long id);
    @Select("SELECT * " +
            "FROM order " +
            "LIMIT #{limit} OFFSET #{offset};")
    List<Order> getOrders(int limit, int offset);
    @Select("SELECT * " +
            "FROM order " +
            "WHERE processed = 0 " +
            "LIMIT #{limit} OFFSET #{offset};")
    List<Order> getUnprocessedOrders(int limit, int offset);

    @Insert("INSERT INTO promo_code(code, expiration, percentage) " +
            "VALUES(#{code}, #{expiration}, #{percentage})")
    void addPromoCode(AddPromoCode promoCode);
   @Update("UPDATE promo_code SET expiration = #{expiration} WHERE code = #{code}")
    void editCodeExpiration(String code, LocalDateTime expiration);

    @Update("UPDATE product SET orders = COALESCE(orders, 0) + #{quantity} WHERE id = #{productId}")
    void addOrderToProduct(Long productId, int quantity);

    @Update("UPDATE `order` SET status = #{status} WHERE order_id = #{orderId}" )
    void updateOrderStatus(String orderId, String status);

    @Select("SELECT status FROM `order` " +
            "WHERE order_id = #{orderId}")
    String getPaymentStatusForOrder(String orderId);

    @Update("UPDATE `order` SET public_id = #{captureId} WHERE order_id = #{orderId}")
    void updatePayPalCaptureId(String value, String id);

    @Select("SELECT type FROM `order` " +
            "WHERE order_id = #{orderId} ")
    String getPaymentTypeByOrderId(String orderId);

    @Delete("DELETE FROM promo_code WHERE code = #{code}")
    void deletePromoCode(String code);

    @Select("SELECT * FROM `order` " +
            "WHERE order_id = #{orderId} ")
    Order getOrderByOrderId(String orderId);
}
