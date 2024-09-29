package com.emazon.cart.infraestructure.util;


public class InfrastructureConstants {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORITIES = "authorities";
    public static final String JWT_KEY_GENERATOR = "${security.jwt.key.private}";
    public static final String JWT_USER_GENERATOR = "${security.jwt.user.generator}";
    public static final String RESTOCK_DAY = "${domain.product.day.restock}";

    public static final String TEMPLATE_RESPONSE_ERROR = "{\"message\": \"%s\"}";


    public static final String BASE_PACKAGE_FEIGN = "com.emazon.cart.infraestructure.output.jpa.feign";
    public static final String FEIGN_NAME = "microservice-stock";
    public static final String FEIGN_STOCK_URL = "${external.stock.api}";

    public static final String TABLE_SHOPPING_CART_ENTITY = "shopping_cart";
    public static final String COLUMN_ID_SHOPPING_CART = "id_shopping_cart";
    public static final String COLUMN_CREATED_DATE = "created_date";
    public static final String COLUMN_UPDATE_DATE = "update_date";

    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized: You need to provide valid credentials to access this resource.";
    public static final String PRODUCT = "product";
    public static final String ACCESS_DENIED = "Access Denied: You do not have permission to access this resource.";

    public static final String CREATED_DATE = "createdDate";
    public static final String UPDATE_DATE = "updateDate";

    private InfrastructureConstants() {
    }
}