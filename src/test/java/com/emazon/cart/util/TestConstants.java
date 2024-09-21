package com.emazon.cart.util;

import java.util.Arrays;
import java.util.List;

public class TestConstants {
    public static final Long VALID_ID = 1L;
    public static final Long VALID_ID_PRODUCT = 1L;
    public static final Integer VALID_AMOUNT = 1;
    public static final List<Long> VALID_LIST_PRODUCTS_IDS = Arrays.asList(1L, 2L, 3L, 4L);
    public static final Long INVALID_ID_PRODUCT = -1L;
    public static final Integer INVALID_AMOUNT = -1;
    public static final Integer INSUFFICIENT_STOCK = 0;

    private TestConstants() {
    }

    public static <T> T getNull() {
        return null;
    }
}
