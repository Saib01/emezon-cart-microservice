package com.emazon.cart.domain.exeption;


public class ProductIdIsInvalidException extends RuntimeException {
    public ProductIdIsInvalidException(ExceptionResponse message) {
        super(message.getMessage());
    }
}

