package com.emazon.cart.infraestructure.output.jpa.adapters;

import com.emazon.cart.domain.spi.IAuthenticationPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class AuthenticationAdapter implements IAuthenticationPersistencePort {
    @Override
    public Long getUserId() {
        return Long.valueOf(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()
                        .toString()
        );
    }
}
