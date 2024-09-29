package com.emazon.cart.infraestructure.configuration.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private static final String ALL_PATHS = "/**";
    private static final String ALLOWED_ORIGIN_LOCALHOST = "http://localhost:4200";
    private static final String[] ALLOWED_METHODS = { "GET", "POST", "PUT", "DELETE", "OPTIONS" };
    private static final String ALL_HEADERS = "*";
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(ALL_PATHS)
                .allowedOrigins(ALLOWED_ORIGIN_LOCALHOST)
                .allowedMethods(ALLOWED_METHODS)
                .allowedHeaders(ALL_HEADERS)
                .allowCredentials(true);
    }

}