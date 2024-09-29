package com.emazon.cart.util;

import java.util.Arrays;
import java.util.List;

public class TestConstants {
    public static final Long VALID_ID = 1L;
    public static final Long VALID_ID_SECOND = 2L;
    public static final Long VALID_ID_PRODUCT = 1L;
    public static final Integer VALID_AMOUNT = 1;
    public static final Integer VALID_AMOUNT_RESTOCK = 100;
    public static final List<Long> VALID_LIST_PRODUCTS_IDS = Arrays.asList(1L, 2L, 3L, 4L);
    public static final List<Long> VALID_LIST_PRODUCT_ID = Arrays.asList(1L, 2L);
    public static final Long INVALID_ID_PRODUCT = -1L;
    public static final Integer INVALID_AMOUNT = -1;
    public static final Integer RESTOCK_DAY = 12;
    public static final Integer ONE = 1;
    public static final String BRAND_FILTER = "brand B";
    public static final String CATEGORY_FILTER = "category A";
    public static final Integer VALID_PAGE = 0;
    public static final Integer VALID_SIZE = 5;
    public static final int VALID_TOTAL_PAGES = 1;
    public static final int VALID_TOTAL_ELEMENTS = 1;
    public static final Integer INVALID_PAGE = -1;
    public static final Integer INVALID_SIZE = -5;
    public static final String INVALID_SORT_DIRECTION = "Aasc";
    public static final String VALID_CATEGORY_NAME = "Electronics";
    public static final String VALID_BRAND_NAME = "Luminix";
    public static final String VALID_PRODUCT_NAME = "UltraClean 3000";
    public static final Double VALID_PRICE = 10000D;

    private TestConstants() {
    }
}
