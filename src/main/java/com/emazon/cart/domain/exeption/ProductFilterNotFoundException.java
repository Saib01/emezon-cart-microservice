package com.emazon.cart.domain.exeption;

public class ProductFilterNotFoundException extends RuntimeException {
    public ProductFilterNotFoundException(ExceptionResponse filterNotFound) {
        super(filterNotFound.getMessage());
    }
}