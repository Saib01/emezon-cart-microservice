package com.emazon.cart.domain.exeption;


public class AmountIsInvalidException extends RuntimeException {
    public AmountIsInvalidException(ExceptionResponse message) {
        super(message.getMessage());
    }
}

