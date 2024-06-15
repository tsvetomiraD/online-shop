package uni.project.online.shop.intercepror;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uni.project.online.shop.service.AuthService;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthInterceptor extends AbstractInterceptor {
    @Autowired
    private AuthService authService;

    public List<String> patterns() {
        return Arrays.asList(
                "/cart/**",
                "/favorites/**",
                "/order/get-payment-methods",
                "/order/delete-payment-method",
                "/products/*/rate",
                "/user/**"
        );
    }

//    @Override
//    public List<String> excludePatterns() {
//        return Arrays.asList(
//                "/api/auth/forgotten-password",
//                "/api/auth/reset-forgotten-password"
//        );
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }

        String authToken = request.getHeader("Authorization");

        if (authToken == null || !authService.isValidToken(authToken)) {
            throw new RuntimeException("Unauthorised");
        }

        request.getSession().setAttribute("token", authToken.contains("Bearer ") ? authToken.replace("Bearer ", "") : authToken);
        return super.preHandle(request, response, handler);
    }
}