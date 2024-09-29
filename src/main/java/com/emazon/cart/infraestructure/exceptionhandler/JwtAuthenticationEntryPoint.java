package com.emazon.cart.infraestructure.exceptionhandler;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.emazon.cart.infraestructure.util.InfrastructureConstants.TEMPLATE_RESPONSE_ERROR;
import static java.lang.String.format;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized: You need to provide valid credentials to access this resource.";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(format(TEMPLATE_RESPONSE_ERROR, UNAUTHORIZED_MESSAGE));
    }
}
