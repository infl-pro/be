package song.mall2.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import song.mall2.interceptor.LogInterceptor;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://52.79.222.161:8080",
                        "http://localhost:3000", "http://52.79.222.161:3000",
                        "https://localhost:443", "https://52.79.222.161:443",
                        "https://shapp.shop", "https://shapp.shop:443",
                        "http://localhost")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(),
                        HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name(),
                        HttpMethod.PATCH.name(), HttpMethod.PUT.name())
                .allowedHeaders("Content-Type", "Authorization", "X-Requested-With")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");
    }
}
