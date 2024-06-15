package uni.project.online.shop.intercepror;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractInterceptor implements AsyncHandlerInterceptor {
    public List<String> patterns() {
        return new ArrayList<>();
    }

    public List<String> excludePatterns() {
        return new ArrayList<>();
    }
}
