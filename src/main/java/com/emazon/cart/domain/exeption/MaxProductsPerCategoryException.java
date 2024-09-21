package com.emazon.cart.domain.exeption;

public class MaxProductsPerCategoryException extends RuntimeException {

    public MaxProductsPerCategoryException(ExceptionResponse message) {
        super(message.getMessage());
    }
}