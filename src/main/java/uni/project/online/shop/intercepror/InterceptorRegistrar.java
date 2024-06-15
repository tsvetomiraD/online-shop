package uni.project.online.shop.intercepror;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.function.Consumer;

@Component
public class InterceptorRegistrar implements Consumer<AbstractInterceptor> {

    private InterceptorRegistry interceptorRegistry;

    @Override
    public void accept(AbstractInterceptor interceptor) {
        InterceptorRegistration registration = this.interceptorRegistry.addInterceptor(interceptor);
        registration.addPathPatterns(interceptor.patterns());
        registration.excludePathPatterns(interceptor.excludePatterns());
    }

    public void setInterceptorRegistry(InterceptorRegistry interceptorRegistry) {
        this.interceptorRegistry = interceptorRegistry;
    }
}

