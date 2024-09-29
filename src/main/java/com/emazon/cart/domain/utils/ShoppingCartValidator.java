package com.emazon.cart.domain.utils;

import com.emazon.cart.domain.exeption.*;
import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;
import com.emazon.cart.domain.spi.IStockPersistencePort;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.emazon.cart.domain.exeption.ExceptionResponse.*;
import static com.emazon.cart.domain.utils.DomainConstants.*;
import static java.lang.String.format;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;


public class ShoppingCartValidator {


    private static final String MONTH_NAME_FORMAT = "MMMM";
    private static final String TEMPLATE_NO_STOCK_ERROR = "The restock is on the %sth of %s.";
    private static final Map<ExceptionResponse, Function<Integer, RuntimeException>> EXCEPTION_MAP = new EnumMap<>(ExceptionResponse.class);

    static {
        EXCEPTION_MAP.put(PRODUCT_ID_INVALID, noMessage -> new ProductIdIsInvalidException(PRODUCT_ID_INVALID));
        EXCEPTION_MAP.put(AMOUNT_INVALID, noMessage -> new AmountIsInvalidException(AMOUNT_INVALID));
        EXCEPTION_MAP.put(INSUFFICIENT_AMOUNT, day -> new InsufficientStockException(messageForInsufficientStock(day)));
        EXCEPTION_MAP.put(SHOPPING_CART_PAGE_NUMBER_IS_INVALID, noMessage -> new ShoppingCartPageNumberIsInvalidException(SHOPPING_CART_PAGE_NUMBER_IS_INVALID));
        EXCEPTION_MAP.put(SHOPPING_CART_PAGE_SIZE_IS_INVALID, noMessage -> new ShoppingCartPageSizeIsInvalidException(SHOPPING_CART_PAGE_SIZE_IS_INVALID));
    }

    private ShoppingCartValidator() {
    }

    public static void addProductToShoppingCart(ShoppingCart shoppingCart, IShoppingCartPersistencePort shoppingCartPersistencePort, IStockPersistencePort productPersistencePort) {
        validateIdProduct(shoppingCart.getIdProduct());
        validateAmount(shoppingCart, productPersistencePort, shoppingCartPersistencePort);
        validateMaxProductPerCategory(shoppingCart, shoppingCartPersistencePort, productPersistencePort);
    }

    public static void getPaginatedProductsInShoppingCart(String sortDirection, int page, int size, String brandName, String categoryName) {
        validateGreaterThanToOne(size, SHOPPING_CART_PAGE_SIZE_IS_INVALID);
        validateGreaterThan(page, SHOPPING_CART_PAGE_NUMBER_IS_INVALID, ZERO, ZERO);
        validateSortDirection(sortDirection);
        if ((brandName == null || brandName.isEmpty()) && (categoryName == null || categoryName.isEmpty())) {
            throw new ProductFilterNotFoundException(PRODUCT_FILTER_NOT_FOUND);
        }
    }

    public static void validateIdProduct(Long id) {
        validateGreaterThanToOne(id, PRODUCT_ID_INVALID);
    }

    private static void validateAmount(ShoppingCart shoppingCart, IStockPersistencePort productPersistencePort, IShoppingCartPersistencePort shoppingCartPersistencePort) {
        validateGreaterThanToOne(shoppingCart.getAmount(), AMOUNT_INVALID);
        validateGreaterThan(productPersistencePort.getAmountByIdProduct(
                        shoppingCart.getIdProduct()),
                INSUFFICIENT_AMOUNT,
                shoppingCart.getAmount(),
                shoppingCartPersistencePort.getRestockDay()
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

    private static <T extends Number> void validateGreaterThanToOne(T number, ExceptionResponse typeException) {
        validateGreaterThan(number, typeException, ONE, ZERO);
    }

    private static <T extends Number> void validateGreaterThan(T number, ExceptionResponse typeException, int threshold, int day) {
        if (number == null || number.intValue() < threshold) {
            throw EXCEPTION_MAP.get(typeException).apply(day);
        }
    }

    private static void validateSortDirection(String sortDirection) {
        if (!(sortDirection.equalsIgnoreCase(ASC) || sortDirection.equalsIgnoreCase(DESC))) {
            throw new ShoppingCartPageSortDirectionIsInvalidException(SHOPPING_CART_PAGE_SORT_DIRECTION_IS_INVALID);
        }
    }


    public static String messageForInsufficientStock(int restockDate) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern(MONTH_NAME_FORMAT);
        String monthName;
        if (today.isAfter(today.withDayOfMonth(restockDate))) {
            LocalDate nextMonth = today.plusMonths(ONE).withDayOfMonth(restockDate);
            monthName = nextMonth.format(monthFormatter);
        } else {
            monthName = today.format(monthFormatter);
        }
        return format(TEMPLATE_NO_STOCK_ERROR, restockDate, monthName);
    }
}
