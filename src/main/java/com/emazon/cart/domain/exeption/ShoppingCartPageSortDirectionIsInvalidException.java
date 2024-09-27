package com.emazon.cart.domain.exeption;


public class ShoppingCartPageSortDirectionIsInvalidException extends RuntimeException {
    public ShoppingCartPageSortDirectionIsInvalidException(ExceptionResponse shoppingCartPageSortDirectionIsInvalid) {
        super(shoppingCartPageSortDirectionIsInvalid.getMessage());
    }
}
