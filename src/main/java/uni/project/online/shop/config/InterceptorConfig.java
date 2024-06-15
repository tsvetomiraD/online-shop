package uni.project.online.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uni.project.online.shop.intercepror.AuthInterceptor;
import uni.project.online.shop.intercepror.InterceptorRegistrar;

@Configuration
@EnableAsync
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private InterceptorRegistrar interceptorRegistrar;
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptorRegistrar.setInterceptorRegistry(registry);
        interceptorRegistrar.accept(authInterceptor);
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE")
                .allowedOrigins("*");
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true);
    }
}
