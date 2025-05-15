package com.pay_my_buddy.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pay_my_buddy.Component.SessionInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private SessionInterceptor sessionInterceptor;

    public WebConfig(SessionInterceptor sessionInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**") // Appliquer à toutes les URLs
                .excludePathPatterns("/login", "/logout", "/register", "/css/**", "/js/**"); // Exclure certaines URLs
    }
}
