package com.emazon.cart.domain.exeption;

public class DuplicateShoppingCartIdException extends RuntimeException {

    public DuplicateShoppingCartIdException(ExceptionResponse message) {
        super(message.getMessage());
    }
}