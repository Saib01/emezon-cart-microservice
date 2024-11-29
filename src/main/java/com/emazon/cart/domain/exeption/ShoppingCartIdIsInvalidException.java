package com.emazon.cart.domain.exeption;


public class ShoppingCartIdIsInvalidException extends RuntimeException {
    public ShoppingCartIdIsInvalidException(ExceptionResponse message) {
        super(message.getMessage());
    }
}

