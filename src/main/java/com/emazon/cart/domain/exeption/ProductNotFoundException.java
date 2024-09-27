package com.emazon.cart.domain.exeption;


public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(ExceptionResponse message) {
        super(message.getMessage());
    }
}
