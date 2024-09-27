package com.emazon.cart.domain.utils;

import com.emazon.cart.domain.exeption.AmountIsInvalidException;
import com.emazon.cart.domain.exeption.InsufficientStockException;
import com.emazon.cart.domain.exeption.MaxProductsPerCategoryException;
import com.emazon.cart.domain.exeption.ProductIdIsInvalidException;
import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.domain.spi.IStockPersistencePort;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.emazon.cart.domain.exeption.ExceptionResponse.*;
import static java.lang.String.format;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;


public class ShoppingCartValidator {


    private static final String MONTH_NAME_FORMAT = "MMMM";
    private static final String TEMPLATE_NO_STOCK_ERROR = "The restock is on the %sth of %s.";
    private static final int MAX_ARTICLES_PER_CATEGORY = 3;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final String[] TYPE_EXCEPTIONS = {"InvalidIdProduct", "InvalidAmount", "InsufficientAmount"};
    private static final Map<String, Function<Integer, RuntimeException>> EXCEPTION_MAP = new HashMap<>();

    static {
        EXCEPTION_MAP.put(TYPE_EXCEPTIONS[ZERO], noMessage -> new ProductIdIsInvalidException(PRODUCT_ID_INVALID));
        EXCEPTION_MAP.put(TYPE_EXCEPTIONS[ONE], noMessage -> new AmountIsInvalidException(AMOUNT_INVALID));
        EXCEPTION_MAP.put(TYPE_EXCEPTIONS[TWO], day -> new InsufficientStockException(messageForExceptionInsufficientStock(day)));
    }

    private ShoppingCartValidator() {
    }

    public static void addProductToShoppingCart(ShoppingCart shoppingCart, IShoppingCartPersistencePort shoppingCartPersistencePort, IStockPersistencePort productPersistencePort) {
        validateIdProduct(shoppingCart.getIdProduct());
        validateAmount(shoppingCart,productPersistencePort,shoppingCartPersistencePort);
        validateMaxProductPerCategory(shoppingCart, shoppingCartPersistencePort,productPersistencePort);
    }
    public static void validateIdProduct(Long id){
        validateGreaterThanOrEqual(id, TYPE_EXCEPTIONS[ZERO]);
    }
    private static void validateAmount(ShoppingCart shoppingCart, IStockPersistencePort productPersistencePort, IShoppingCartPersistencePort shoppingCartPersistencePort){
        validateGreaterThanOrEqual(shoppingCart.getAmount(), TYPE_EXCEPTIONS[ONE]);
        validateGreaterThanOrEqual(productPersistencePort.getAmountByIdProduct(shoppingCart.getIdProduct()), TYPE_EXCEPTIONS[TWO],
                shoppingCart.getAmount(),shoppingCartPersistencePort.getRestockDay()
        );
    }
    private static void validateMaxProductPerCategory(ShoppingCart shoppingCart, IShoppingCartPersistencePort shoppingCartPersistencePort, IStockPersistencePort productPersistencePort) {

        ShoppingCart shoppingCartExist = shoppingCartPersistencePort.findByIdUserAndIdProduct(shoppingCart.getIdUser(), shoppingCart.getIdProduct());
        if (shoppingCartExist != null) {
            shoppingCart.setId(shoppingCartExist.getId());
        }

        List<Long> productIds = new ArrayList<>(shoppingCartPersistencePort.getProductIds(shoppingCart.getIdUser()));
        productIds.add(shoppingCart.getIdProduct());
        if (productIds.size() > MAX_ARTICLES_PER_CATEGORY && !productPersistencePort.validateMaxProductPerCategory(productIds)) {
            throw new MaxProductsPerCategoryException(OUT_OF_STOCK);
        }
    }
    private static <T extends Number> void validateGreaterThanOrEqual(T number, String typeException) {
        validateGreaterThanOrEqual(number,typeException,ONE, ZERO);
    }
    private static <T extends Number> void validateGreaterThanOrEqual(T number, String typeException, int threshold,int day) {
        if (number.intValue() < threshold) {
            throw EXCEPTION_MAP.get(typeException).apply(day);
        }
    }

    private static String messageForExceptionInsufficientStock(int restockDate) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern(MONTH_NAME_FORMAT);
        String monthName;
        if (today.isAfter(today.withDayOfMonth(restockDate))) {
            LocalDate nextMonth = today.plusMonths(ONE).withDayOfMonth(restockDate);
            monthName = nextMonth.format(monthFormatter);
        } else {
            monthName = today.format(monthFormatter);
        }
        return format(TEMPLATE_NO_STOCK_ERROR,restockDate, monthName);
    }
}
