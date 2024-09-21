package com.emazon.cart.domain.exeption;

public class MaxProductsPerCategoryException extends RuntimeException {

    public MaxProductsPerCategoryException(String message) {
        super(message);
    }
}