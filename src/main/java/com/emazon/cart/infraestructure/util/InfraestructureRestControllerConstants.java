package com.emazon.cart.infraestructure.util;


public class InfraestructureRestControllerConstants {
    public static final String RESPONSE_CODE_SUCCESS = "200";
    public static final String RESPONSE_CODE_BAD_REQUEST = "400";
    public static final String RESPONSE_CODE_CONFLICT = "409";
    public static final String RESPONSE_CODE_BAD_REQUEST_DESCRIPTION = "Invalid request data";
    public static final String RESPONSE_CODE_CONFLICT_DESCRIPTION = "Internal server error";
    public static final String RESPONSE_DESCRIPTION_ADD_SUCCESSFUL = "Product added successfully";
    public static final String SUMMARY_ADD_PRODUCT = "Add product to shopping cart";
    public static final String API_BASE = "/api";
    public static final String CLIENT = "CLIENT";
    public static final String SHOPPING_CART = "/shopping-cart";
    public static final String ADD_PRODUCT = "/add-product";

    public static final String API_ADD_PRODUCT = String.format("%s%s%s", API_BASE, SHOPPING_CART, ADD_PRODUCT);

    private InfraestructureRestControllerConstants() {

    }
}
