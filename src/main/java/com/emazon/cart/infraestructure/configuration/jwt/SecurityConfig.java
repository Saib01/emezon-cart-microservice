package com.emazon.cart.infraestructure.configuration.jwt;
import com.emazon.cart.infraestructure.configuration.jwt.jwt.JwtAuthenticationFilter;
import com.emazon.cart.infraestructure.configuration.jwt.jwt.JwtRequestInterceptor;
import com.emazon.cart.infraestructure.configuration.jwt.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtils jwtUtils;
    private final JwtRequestInterceptor jwtRequestInterceptor;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(http->{
                    http.requestMatchers(HttpMethod.POST, "/api/cart/*/add-item").hasAnyRole("CLIENT");
                    http.requestMatchers(HttpMethod.DELETE, "/api/cart/*/remove-item/*").hasAnyRole("CLIENT");
                    http.requestMatchers(HttpMethod.POST, "/api/purchase").hasAnyRole("CLIENT");
                    http.anyRequest().permitAll();
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils,jwtRequestInterceptor), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}