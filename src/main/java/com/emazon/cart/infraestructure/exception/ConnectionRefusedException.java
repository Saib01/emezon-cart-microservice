package com.emazon.cart.infraestructure.exception;


public class ConnectionRefusedException extends RuntimeException {
    public ConnectionRefusedException(String message) {
        super(message
        );
    }
}
