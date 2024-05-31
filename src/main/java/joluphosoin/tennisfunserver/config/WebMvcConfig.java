package joluphosoin.tennisfunserver.config;

import joluphosoin.tennisfunserver.intercepter.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/user/register",
                        "/api/user/login",
                        "/api/user/verify-email",
                        "/swagger-ui/**",
                        "/api-docs/**",
                        "/v3/api-docs/**",
                        "/auth/expired",
                        "/api/business/register",
                        "/api/business/login,",
                        "/success.html",
                        "/fail.hmtl",
                        "/cancel.html");
    }

}