package com.emazon.cart.infraestructure.configuration.jwt.jwt;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class JwtRequestInterceptor implements RequestInterceptor {

    private String jwtToken;

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public void apply(RequestTemplate template) {
            if (jwtToken != null) {
            template.header("Authorization", "Bearer " + jwtToken);
            jwtToken = null;
        }
    }
}
