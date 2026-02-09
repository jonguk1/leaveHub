package com.example.leaveHub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import com.example.leaveHub.common.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                // 모든 경로에 대해 인터셉터 적용
                .addPathPatterns("/**")
                // 단, 다음 경로들은 제외
                .excludePathPatterns(
                        "/",
                        "/login",
                        "/logout",
                        "/error/**",
                        "/resources/**",
                        "/static/**");
    }
}
