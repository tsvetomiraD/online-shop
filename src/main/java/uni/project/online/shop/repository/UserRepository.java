package uni.project.online.shop.repository;

import org.apache.ibatis.annotations.*;
import uni.project.online.shop.model.PurchaseHistory;
import uni.project.online.shop.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserRepository {
    @Select("SELECT * FROM user WHERE email = #{email}")
    User getUserByEmail(String email);

    @Insert("INSERT INTO user(email, password_hash, name) " +
            "VALUES(#{email}, #{password}, #{name})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void addUser(User user);

    @Insert("INSERT INTO activation_token(user_id, token, valid_until, type) " +
            "VALUES(#{userId}, #{token}, #{expirationTime}, #{type})")
    void addActivationToken(Long userId, String token, LocalDateTime expirationTime, String type);

    @Select("SELECT u.* " +
            "FROM auth_token at " +
            "JOIN user u ON u.id = at.user_id " +
            "WHERE at.token = #{token}")
    User getUserByAuthToken(String token);
    @Select("SELECT u.* " +
            "FROM activation_token at " +
            "JOIN user u ON u.id = at.user_id " +
            "WHERE at.token = #{token}")
    User getUserByActivationToken(String token);


    @Select("SELECT valid_until FROM activation_token WHERE token = #{token}")
    LocalDateTime getExpiration(String token);

    @Delete("DELETE FROM activation_token WHERE token = #{token}")
    void deleteToken(String token);
    @Update("UPDATE user SET verified = 1 WHERE id = #{id}")
    void updateRegStatus(Long id);

    @Insert("INSERT INTO promo_code(code, expiration, percentage) " +
            "VALUES(#{code}, #{expiration}, 5)")
    void addUserPromoCode(String code, LocalDateTime expiration);

    @Select("SELECT expiration FROM auth_token WHERE user_id = #{id} AND type = #{type}")
    LocalDateTime getToken(Long id, String type);

    @Update("UPDATE user SET password_hash = #{passwordHash} WHERE id = #{id}")
    void updatePassword(Long id, String passwordHash);
    @Select("SELECT code FROM promo_code WHERE code = #{code}")
    String checkCode(String code);

    @Select("SELECT * FROM user WHERE id = #{userId}")
    User getUserById(Long userId);

    @Update("UPDATE user SET customer_id = #{customerId} WHERE id = #{id}")
    void editUserCustomerId(User user);

    @Select("SELECT date_created, total_price AS price, `status`, id FROM `order` WHERE user_id = #{userId}")
    List<PurchaseHistory> getUserPurchaseHistory(Long userId);
}
