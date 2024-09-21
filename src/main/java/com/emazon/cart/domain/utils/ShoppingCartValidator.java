package com.emazon.cart.domain.utils;

import com.emazon.cart.domain.exeption.AmountIsInvalidException;
import com.emazon.cart.domain.exeption.InsufficientStockException;
import com.emazon.cart.domain.exeption.MaxProductsPerCategoryException;
import com.emazon.cart.domain.exeption.ProductIdIsInvalidException;
import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.emazon.cart.domain.exeption.ExceptionResponse.*;
import static java.lang.String.format;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;


public class ShoppingCartValidator {


    public static final String MONTH_NAME_FORMAT = "MMMM";
    public static final String TEMPLATE_NO_STOCK_ERROR = "The restock is on the 15th of %s.";
    public static final int MAX_ARTICLES_PER_CATEGORY = 3;
    private static final int RESTOCK_DATE = 15;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final String[] TYPE_EXCEPTIONS = {"InvalidIdProduct", "InvalidAmount", "InsufficientAmount"};
    private static final Map<String, Supplier<RuntimeException>> EXCEPTION_MAP = new HashMap<>();

    static {
        EXCEPTION_MAP.put(TYPE_EXCEPTIONS[ZERO], () -> new ProductIdIsInvalidException(PRODUCT_ID_INVALID.getMessage()));
        EXCEPTION_MAP.put(TYPE_EXCEPTIONS[ONE], () -> new AmountIsInvalidException(AMOUNT_INVALID.getMessage()));
        EXCEPTION_MAP.put(TYPE_EXCEPTIONS[TWO], () -> new InsufficientStockException(messageForExceptionInsufficientStock()));
    }

    private ShoppingCartValidator() {
    }

    public static void addProductToShoppingCart(ShoppingCart shoppingCart, IShoppingCartPersistencePort shoppingCartPersistencePort) {
        validateGreaterThanOrEqual(shoppingCart.getIdProduct(), TYPE_EXCEPTIONS[ZERO], ONE);
        validateGreaterThanOrEqual(shoppingCart.getAmount().longValue(), TYPE_EXCEPTIONS[ONE], ONE);
        validateGreaterThanOrEqual(shoppingCartPersistencePort.getAmountByIdProduct(shoppingCart.getIdProduct()).longValue(), TYPE_EXCEPTIONS[TWO],
                shoppingCart.getAmount()
        );
        validateMaxProductPerCategory(shoppingCart, shoppingCartPersistencePort);
    }

    private static void validateMaxProductPerCategory(ShoppingCart shoppingCart, IShoppingCartPersistencePort shoppingCartPersistencePort) {

        ShoppingCart shoppingCartExist = shoppingCartPersistencePort.findByIdUserAndIdProduct(shoppingCart.getIdUser(), shoppingCart.getIdProduct());
        if (shoppingCartExist != null) {
            shoppingCart.setId(shoppingCartExist.getId());
        }

        List<Long> productIds = new ArrayList<>(shoppingCartPersistencePort.getProductIds(shoppingCart.getIdUser()));
        productIds.add(shoppingCart.getIdProduct());
        if (productIds.size() > MAX_ARTICLES_PER_CATEGORY && !shoppingCartPersistencePort.validateMaxProductPerCategory(productIds)) {
            throw new MaxProductsPerCategoryException(OUT_OF_STOCK.getMessage());
        }
    }

    private static void validateGreaterThanOrEqual(Long number, String typeException, int threshold) {
        if (number < threshold) {
            throw EXCEPTION_MAP.get(typeException).get();
        }
    }

    private static String messageForExceptionInsufficientStock() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern(MONTH_NAME_FORMAT);
        String monthName;
        if (today.isAfter(today.withDayOfMonth(RESTOCK_DATE))) {
            LocalDate nextMonth = today.plusMonths(ONE).withDayOfMonth(RESTOCK_DATE);
            monthName = nextMonth.format(monthFormatter);
        } else {
            monthName = today.format(monthFormatter);
        }
        return format(TEMPLATE_NO_STOCK_ERROR, monthName);
    }
}
