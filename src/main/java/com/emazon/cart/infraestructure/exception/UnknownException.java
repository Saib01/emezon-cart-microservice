package com.emazon.cart.infraestructure.exception;


import com.emazon.cart.domain.exeption.ExceptionResponse;

public class UnknownException extends RuntimeException {
    public UnknownException(ExceptionResponse message) {
        super(message.getMessage());
    }
}

