package com.emazon.cart.infraestructure.output.jpa.adapters;

import com.emazon.cart.application.dtos.ProductDto;
import com.emazon.cart.application.dtos.ShoppingCartListRequest;
import com.emazon.cart.domain.model.Brand;
import com.emazon.cart.domain.model.Category;
import com.emazon.cart.domain.model.PageShopping;
import com.emazon.cart.domain.model.Product;
import com.emazon.cart.infraestructure.output.jpa.feign.StockFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.emazon.cart.domain.utils.DomainConstants.ASC;
import static com.emazon.cart.util.TestConstants.*;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class StockFeignAdapterTest {

    @InjectMocks
    private StockFeignAdapter stockFeignAdapter;
    @Mock
    private StockFeignClient stockFeignClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return amount of product by product ID")
    void shouldReturnAmountByIdProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setAmount(VALID_AMOUNT);
        when(stockFeignClient.getProductById(VALID_ID_PRODUCT)).thenReturn(productDto);

        Integer result = stockFeignAdapter.getAmountByIdProduct(VALID_ID_PRODUCT);

        assertEquals(VALID_AMOUNT, result);
    }

    @Test
    @DisplayName("Should validate max product per category")
    void shouldValidateMaxProductPerCategory() {
        when(stockFeignClient.validateMaxProductPerCategory(VALID_LIST_PRODUCTS_IDS)).thenReturn(TRUE);

        boolean result = stockFeignAdapter.validateMaxProductPerCategory(VALID_LIST_PRODUCTS_IDS);

        assertTrue(result);
    }

    @Test
    @DisplayName("Should  return paginated product")
    void shouldGetPaginatedProductsInShoppingCart() {
        PageShopping<Product> productPageShopping = new PageShopping<>(
                List.of(new Product(VALID_ID_PRODUCT, VALID_PRODUCT_NAME, VALID_AMOUNT, null, VALID_PRICE,
                        new Brand(VALID_ID, VALID_BRAND_NAME),
                        new ArrayList<>(List.of(new Category(VALID_ID, VALID_CATEGORY_NAME))))
                ), VALID_TOTAL_ELEMENTS, VALID_TOTAL_PAGES, true, true, VALID_SIZE
        );
        when(stockFeignClient.getPaginatedProductsInShoppingCart(
                any(ShoppingCartListRequest.class),
                eq(ASC),
                eq(VALID_PAGE),
                eq(VALID_SIZE))
        ).thenReturn(productPageShopping);
        PageShopping<Product> result = stockFeignAdapter.getPaginatedProductsInShoppingCart(VALID_LIST_PRODUCT_ID, BRAND_FILTER, CATEGORY_FILTER, ASC, VALID_PAGE, VALID_SIZE);
        assertEquals(productPageShopping, result);
    }
}