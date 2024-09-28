package com.emazon.cart.infraestructure.output.jpa.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.emazon.cart.util.TestConstants.VALID_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationAdapterTest {
    @InjectMocks
    private AuthenticationAdapter authenticationAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return the user ID from security context")
    void shouldReturnUserIdFromSecurityContext() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(VALID_ID.toString());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        Long result = authenticationAdapter.getUserId();

        assertEquals(VALID_ID, result);
    }
}