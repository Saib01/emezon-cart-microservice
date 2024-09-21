package com.emazon.cart.domain.exeption;


public class ProductIdIsInvalidException extends RuntimeException {
    public ProductIdIsInvalidException(String message) {
        super(message);
    }
}

