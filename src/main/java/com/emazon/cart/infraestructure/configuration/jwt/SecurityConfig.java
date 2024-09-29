package com.emazon.cart.infraestructure.configuration.jwt;

import com.emazon.cart.infraestructure.configuration.jwt.jwt.JwtAuthenticationFilter;
import com.emazon.cart.infraestructure.configuration.jwt.jwt.JwtRequestInterceptor;
import com.emazon.cart.infraestructure.configuration.jwt.jwt.JwtUtils;
import com.emazon.cart.infraestructure.exceptionhandler.CustomAccessDeniedHandler;
import com.emazon.cart.infraestructure.exceptionhandler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.emazon.cart.infraestructure.util.InfraestructureRestControllerConstants.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtils jwtUtils;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestInterceptor jwtRequestInterceptor;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(HttpMethod.POST, API_ADD_PRODUCT).hasAnyRole(CLIENT);
                    http.requestMatchers(HttpMethod.DELETE, API_ADD_REMOVE).hasAnyRole(CLIENT);
                    http.requestMatchers(HttpMethod.GET, "/api/shopping-cart").hasAnyRole(CLIENT);
                    http.requestMatchers(HttpMethod.POST, "/api/purchase").hasAnyRole(CLIENT);
                    http.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils, jwtRequestInterceptor), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .build();
    }

}