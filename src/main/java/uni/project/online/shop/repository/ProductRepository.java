package uni.project.online.shop.repository;

import org.apache.ibatis.annotations.*;
import uni.project.online.shop.model.AvgRating;
import uni.project.online.shop.model.Product;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductRepository { //todo add left joins
    List<Product> getProducts(Map<String, Object> filters);
    @Select("SELECT p.*, t.name AS type, c.name AS category, b.name AS brand, tg.name AS targetGroup " +
            "FROM product p " +
            "LEFT JOIN type t ON p.type_id = t.id " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "LEFT JOIN brand b ON p.brand_id = b.id " +
            "LEFT JOIN target_group tg ON p.target_group_id = tg.id " +
            "WHERE p.on_sale = 1 ")
    List<Product> getProductsOnSale();

    @Select("SELECT p.*, t.name AS type, c.name AS category, b.name AS brand, tg.name AS targetGroup " +
            "FROM product p " +
            "LEFT JOIN type t ON p.type_id = t.id " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "LEFT JOIN brand b ON p.brand_id = b.id " +
            "LEFT JOIN target_group tg ON p.target_group_id = tg.id " +
            //"WHERE p.orders IS NOT NULL " +
            "ORDER BY p.${fieldName} DESC LIMIT 8") //todo
    List<Product> getHomePageCategories(@Param("fieldName") String fieldName);

    @Select("SELECT p.*, t.name AS type, c.name AS category, b.name AS brand, tg.name AS targetGroup " +
            "FROM product p " +
            "LEFT JOIN type t ON p.type_id = t.id " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "LEFT JOIN brand b ON p.brand_id = b.id " +
            "LEFT JOIN target_group tg ON p.target_group_id = tg.id " +
            "WHERE b.name = #{brand} ")
    List<Product> getProductsByBrand(String brand);

    @Select("SELECT p.*, t.name AS type, c.name AS category, b.name AS brand, tg.name AS targetGroup " +
            "FROM product p " +
            "LEFT JOIN type t ON p.type_id = t.id " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "LEFT JOIN brand b ON p.brand_id = b.id " +
            "LEFT JOIN target_group tg ON p.target_group_id = tg.id " +
            "WHERE p.name OR p.description LIKE CONCAT('%', #{search}, '%') ")
    List<Product> searchInProducts(String search);

    @Select("SELECT p.*, t.name AS type, c.name AS category, b.name AS brand, tg.name AS targetGroup " +
            "FROM product p " +
            "LEFT JOIN type t ON p.type_id = t.id " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "LEFT JOIN brand b ON p.brand_id = b.id " +
            "LEFT JOIN target_group tg ON p.target_group_id = tg.id " +
            "WHERE p.id = #{productId} ")
    Product getProduct(Long productId);

    @Select("SELECT rate " +
            "FROM product_rate " +
            "WHERE product_id = #{id} AND user_id = #{userId};")
    public Double getRating(Long id, Long userId);

    @Update("UPDATE product_rate SET rate = #{rating} WHERE user_id = #{userId} AND product_id = #{id};")
    void updateUserRating(Long id, Long userId, Double rating);
    @Insert("INSERT INTO product_rate(user_id, product_id, rate) " +
            "VALUES (#{userId},#{id},#{rating});")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void rate(Long id, Long userId, Double rating);

    @Select("SELECT SUM(pr.rate) AS rate, COUNT(pr.id) AS count " +
            "FROM product p " +
            "LEFT JOIN product_rate pr ON pr.product_id = p.id " +
            "WHERE p.id = #{id}" )
    AvgRating getRate(Long id);
    @Update("UPDATE product SET rate = #{rate} " +
            "WHERE id = #{id}")
    void setRate(Long id, double rate);
}
