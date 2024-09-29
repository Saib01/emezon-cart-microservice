package com.emazon.cart.domain.exeption;


public class ShoppingCartNotFoundException extends RuntimeException {
    public ShoppingCartNotFoundException(ExceptionResponse message) {
        super(message.getMessage());
    }
}
