package com.emazon.cart.infraestructure.util;


public class InfraestructureRestControllerConstants {
    public static final String RESPONSE_CODE_SUCCESS = "200";
    public static final String RESPONSE_CODE_BAD_REQUEST = "400";
    public static final String RESPONSE_CODE_NOT_FOUND = "404";
    public static final String RESPONSE_CODE_CONFLICT = "409";
    public static final String RESPONSE_CODE_INTERNAL_SERVER = "500";
    public static final String BRAND_NAME = "brandName";
    public static final String CATEGORY_NAME = "categoryName";

    public static final String PRODUCTS_RETRIEVED_SUCCESSFULLY = "Products retrieved successfully";
    public static final String INVALID_PARAMETERS = "Invalid parameters";
    public static final String REMOVE_PRODUCTS_FROM_THE_SHOPPING_CART = "Remove products from the shopping cart";
    public static final String REMOVES_PRODUCTS_IDENTIFIED_BY_THEIR_IDS_FROM_THE_SHOPPING_CART = "Removes products identified by their IDs from the shopping cart.";
    public static final String PRODUCTS_SUCCESSFULLY_REMOVED_FROM_THE_SHOPPING_CART = "Products successfully removed from the shopping cart";
    public static final String INVALID_OR_MISSING_PRODUCT_ID_LIST = "Invalid or missing product ID list";
    public static final String ONE_OR_MORE_PRODUCT_IDS_NOT_FOUND_IN_THE_SHOPPING_CART = "One or more product IDs not found in the shopping cart";
    public static final String LIST_ID = "listId";
    public static final String RESTORE_PRODUCTS_IN_THE_SHOPPING_CART = "Restore products in the shopping cart";
    public static final String RESTORES_PRODUCTS_IN_THE_SHOPPING_CART_FROM_A_PROVIDED_LIST_OF_SHOPPING_CART_ITEMS = "Restores products in the shopping cart from a provided list of shopping cart items.";
    public static final String PRODUCTS_SUCCESSFULLY_RESTORED_IN_THE_SHOPPING_CART = "Products successfully restored in the shopping cart";
    public static final String INVALID_SHOPPING_CART_LIST = "Invalid shopping cart list";
    public static final String INTERNAL_SERVER_ERROR_UNABLE_TO_RESTORE_PRODUCTS = "Internal server error, unable to restore products";
    public static final String PRODUCTS_SUCCESSFULLY_RESTORED = "Products Successfully Restored";
    public static final String RETRIEVE_SHOPPING_CART_ITEMS_BY_PRODUCT_IDS = "Retrieve shopping cart items by product IDs";
    public static final String FETCH_A_LIST_OF_SHOPPING_CART_ITEMS_USING_A_LIST_OF_PRODUCT_IDS = "Fetch a list of shopping cart items using a list of product IDs.";
    public static final String SHOPPING_CART_ITEMS_RETRIEVED_SUCCESSFULLY = "Shopping cart items retrieved successfully";
    public static final String INVALID_OR_EMPTY_PRODUCT_ID_LIST = "Invalid or empty product ID list";
    public static final String NO_SHOPPING_CART_ITEMS_FOUND_FOR_THE_PROVIDED_PRODUCT_IDS = "No shopping cart items found for the provided product IDs";
    public static final String GET_TOTAL_NUMBER_OF_SHOPPING_CART_ITEMS_FOR_THE_USER = "Get total number of shopping cart items for the user";
    public static final String RETRIEVE_THE_TOTAL_NUMBER_OF_ITEMS_IN_THE_SHOPPING_CART_FOR_THE_CURRENT_USER = "Retrieve the total number of items in the shopping cart for the current user.";
    public static final String TOTAL_NUMBER_OF_ITEMS_IN_THE_SHOPPING_CART_RETRIEVED_SUCCESSFULLY = "Total number of items in the shopping cart retrieved successfully";
    public static final String INVALID_USER_OR_SHOPPING_CART_DATA = "Invalid user or shopping cart data";
    public static final String SHOPPING_CART_NOT_FOUND_FOR_THE_USER = "Shopping cart not found for the user";

    public static final String RESPONSE_CODE_BAD_REQUEST_DESCRIPTION = "Invalid request data";
    public static final String RESPONSE_CODE_CONFLICT_DESCRIPTION = "Internal server error";
    public static final String RESPONSE_DESCRIPTION_ADD_SUCCESSFUL = "Product added successfully";
    public static final String RESPONSE_DESCRIPTION_REMOVE_SUCCESSFUL = "Product removed successfully";
    public static final String RESPONSE_DESCRIPTION_REMOVE_LIST_IDS_SUCCESSFUL = "Product list removed successfully";
    public static final String RESPONSE_GET_PRODUCTS = "Fetch a paginated list of products from the shopping cart, optionally filtered by brand and category.";

    public static final String SUMMARY_ADD_PRODUCT = "Add product to shopping cart";
    public static final String SUMMARY_REMOVE_PRODUCT = "Remove product to shopping cart";
    public static final String SUMMARY_GET_PRODUCTS = "Retrieve paginated products in the shopping cart";

    public static final String CLIENT = "CLIENT";


    public static final String SORT_DIRECTION = "sortDirection";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";

    private InfraestructureRestControllerConstants() {

    }
}
