package uni.project.online.shop.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncoder {
    static final MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(CharSequence rawPassword) {
        md.update(rawPassword.toString().getBytes());
        byte[] digest = md.digest();
        md.reset();
        return new String(digest);
    }


    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encode = encode(rawPassword);
        return encode.equals(encodedPassword);
    }
}