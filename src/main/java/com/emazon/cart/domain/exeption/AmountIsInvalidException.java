package com.emazon.cart.domain.exeption;


public class AmountIsInvalidException extends RuntimeException {
    public AmountIsInvalidException(String message) {
        super(message);
    }
}

