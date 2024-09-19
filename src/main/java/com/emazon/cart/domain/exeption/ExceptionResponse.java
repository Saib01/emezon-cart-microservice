package com.emazon.cart.domain.exeption;


public enum ExceptionResponse {

    PRODUCT_ID_INVALID("Product ID must be greater than zero"),
    AMOUNT_INVALID("Amount must be greater than zero"),
    OUT_OF_STOCK("The maximum number of products per category has been reached."),
    STOCK_FEIGN_PRODUCT_NOT_FOUND("The product was not found"),
    UNKNOWN_ERROR("An unknown error has occurred. Please try again later or contact support if the issue persists.");
    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}