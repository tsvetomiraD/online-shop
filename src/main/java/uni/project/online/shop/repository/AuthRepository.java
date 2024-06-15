package uni.project.online.shop.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import uni.project.online.shop.model.auth.AuthToken;

import java.time.LocalDateTime;

@Mapper
public interface AuthRepository {
    @Select("SELECT * FROM auth_token WHERE user_id = #{i}")
    AuthToken getTokenByUserId(Long id);

    @Insert("INSERT IGNORE INTO auth_token (user_id, token, valid_until) VALUES (#{id}, #{token}, #{validUntil})")
    void insertTokenForUser(Long id, String token, LocalDateTime validUntil);

    @Update("UPDATE auth_token SET valid_until = #{validUntil} WHERE id = #{id}")
    void updateToken(AuthToken tokenByUserId);

    @Select("SELECT user_id FROM auth_token WHERE token = #{token} AND valid_until > NOW()")
    Long clientIdByValidToken(String token);
}
