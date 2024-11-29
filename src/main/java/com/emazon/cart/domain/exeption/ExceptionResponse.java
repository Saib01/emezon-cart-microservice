package com.emazon.cart.domain.exeption;


public enum ExceptionResponse {

    PRODUCT_ID_INVALID("Product ID must be greater than zero"),
    SHOPPING_CART_ID_INVALID("Product ID must be greater than zero"),
    AMOUNT_INVALID("Amount must be greater than zero"),
    OUT_OF_STOCK("The maximum number of products per category has been reached."),
    PRODUCT_NOT_FOUND("The product was not found"),
    SHOPPING_CART_NOT_FOUND("Shopping cart Id was not found"),
    UNKNOWN_ERROR("An unknown error has occurred. Please try again later or contact support if the issue persists."),
    SHOPPING_CART_PAGE_SORT_DIRECTION_IS_INVALID("The sort direction for retrieving the shoppingCart page is invalid. Please use 'ASC' for ascending or 'DESC' for descending."),
    SHOPPING_CART_PAGE_NUMBER_IS_INVALID("The page number for shoppingCart must be greater than or equal to zero."),
    SHOPPING_CART_PAGE_SIZE_IS_INVALID("The page size to retrieve the shoppingCart page must be greater than or equal to one."),
    INSUFFICIENT_AMOUNT("insufficientAmount"),
    PRODUCT_FILTER_NOT_FOUND("Product Filter not found"),
    JWT_INVALID("Token Invalid, not Authorized"),
    CONNECTION_REFUSED("Service unavailable. Please try again later.");
    ;
    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}