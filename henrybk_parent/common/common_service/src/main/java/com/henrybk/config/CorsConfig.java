package com.henrybk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description 跨域请求配置类
 * @author Henry
 * @since 2023-05-18
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//允许所有的访问请求（访问路径）
                .allowedMethods("*")//允许所有的请求方法访问该跨域资源服务器
                .allowedOrigins("*")//允许所有的请求域名访问我们的跨域资源
                .allowedHeaders("*");//允许所有的请求header访问
    }
}
