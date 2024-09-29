package com.emazon.cart.domain.exeption;


public class ShoppingCartPageNumberIsInvalidException extends RuntimeException {
    public ShoppingCartPageNumberIsInvalidException(ExceptionResponse shoppingCartPageNumberIsInvalid) {
        super(shoppingCartPageNumberIsInvalid.getMessage());
    }
}
