package com.emazon.cart.application.handler.implement;

import com.emazon.cart.application.dtos.ShoppingCartRequest;
import com.emazon.cart.application.mapper.ShoppingCartRequestMapper;
import com.emazon.cart.domain.api.IShoppingCartServicePort;
import com.emazon.cart.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.emazon.cart.domain.utils.DomainConstants.ASC;
import static com.emazon.cart.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ShoppingCartHandlerTest {
    @Mock
    private IShoppingCartServicePort shoppingCartServicePort;

    @Mock
    private ShoppingCartRequestMapper shoppingCartRequestMapper;

    @InjectMocks
    private ShoppingCartHandler shoppingCartHandler;

    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingCart = new ShoppingCart(null, null, VALID_ID_PRODUCT, VALID_AMOUNT);
    }

    @Test
    @DisplayName("Should save shoppingCart correctly")
    void shouldSaveShoppingCart() {
        ShoppingCartRequest shoppingCartRequest = new ShoppingCartRequest(VALID_AMOUNT, VALID_ID_PRODUCT);

        when(shoppingCartRequestMapper.toShoppingCart(shoppingCartRequest)).thenReturn(shoppingCart);

        shoppingCartHandler.addProductToShoppingCart(shoppingCartRequest);

        ArgumentCaptor<ShoppingCart> shoppingCartRequestCaptor = ArgumentCaptor.forClass(ShoppingCart.class);

        verify(shoppingCartServicePort).addProductToShoppingCart(shoppingCartRequestCaptor.capture());
        assertEquals(shoppingCartRequestCaptor.getValue(), shoppingCart);
    }

    @Test
    @DisplayName("Should remove product from shopping cart successfully")
    void shouldRemoveProductFromShoppingCartSuccessfully() {
        shoppingCartHandler.removeProductFromShoppingCart(VALID_ID);

        ArgumentCaptor<Long> productIdCapture = ArgumentCaptor.forClass(Long.class);

        verify(shoppingCartServicePort).removeProductFromShoppingCart(productIdCapture.capture());
        assertEquals(VALID_ID, productIdCapture.getValue());
    }

    @Test
    @DisplayName("Should  return paginated products in shopping cart")
    void shouldGetPaginatedProductsInShoppingCart() {
        PageShopping<Product> productPageShopping = new PageShopping<>(
                List.of(new Product(VALID_ID_PRODUCT, VALID_PRODUCT_NAME, VALID_AMOUNT, null, VALID_PRICE,
                        new Brand(VALID_ID, VALID_BRAND_NAME),
                        new ArrayList<>(Collections.singletonList(new Category(VALID_ID, VALID_CATEGORY_NAME))))
                ), VALID_TOTAL_ELEMENTS, VALID_TOTAL_PAGES, true, true, VALID_SIZE
        );

        when(shoppingCartServicePort.getPaginatedProductsInShoppingCart(BRAND_FILTER, CATEGORY_FILTER, ASC, VALID_PAGE, VALID_SIZE))
                .thenReturn(productPageShopping);

        PageShopping<Product> result = shoppingCartServicePort.getPaginatedProductsInShoppingCart(BRAND_FILTER, CATEGORY_FILTER, ASC, VALID_PAGE, VALID_SIZE);
        assertEquals(productPageShopping, result);
    }
}