package uni.project.online.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Base64;
import java.util.Random;

@Configuration
@EnableAsync
public class AuthConfig {

    @Bean
    public Base64.Decoder getDecoder() {
        return Base64.getDecoder();
    }

    @Bean
    public Base64.Encoder getEncoder() {
        return Base64.getEncoder();
    }

    @Bean
    public Random getRandom() {
        return new Random();
    }
}
