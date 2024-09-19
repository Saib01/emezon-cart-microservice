package com.emazon.cart.infraestructure.exception;


import com.emazon.cart.domain.exeption.ExceptionResponse;

public class FeignProductNotFoundException extends RuntimeException {
    public FeignProductNotFoundException(ExceptionResponse message) {
        super(message.getMessage());
    }
}
