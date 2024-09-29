package com.emazon.cart.domain.exeption;


public class ShoppingCartPageSizeIsInvalidException extends RuntimeException {
    public ShoppingCartPageSizeIsInvalidException(ExceptionResponse shoppingCartPageSizeNumberIsInvalid) {
        super(shoppingCartPageSizeNumberIsInvalid.getMessage());
    }
}
