package com.emazon.cart.infraestructure.exceptionhandler;

import com.emazon.cart.domain.exeption.ExceptionResponse;
import com.emazon.cart.infraestructure.exception.ConnectionRefusedException;

public class ConnectionRefused {
    private static final String CONNECTION_ERROR="Connection";
    public static void throwIfConnectionRefused(String message) {
        if (message.contains(CONNECTION_ERROR)) {
            throw new ConnectionRefusedException(ExceptionResponse.CONNECTION_REFUSED.getMessage());
        }
    }

    private ConnectionRefused() {
    }
}
