package com.emazon.cart.infraestructure.exceptionhandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.emazon.cart.infraestructure.util.InfrastructureConstants.ACCESS_DENIED;
import static com.emazon.cart.infraestructure.util.InfrastructureConstants.TEMPLATE_RESPONSE_ERROR;
import static java.lang.String.format;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(format(TEMPLATE_RESPONSE_ERROR, ACCESS_DENIED));
    }
}
