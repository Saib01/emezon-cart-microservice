package com.emazon.cart.infraestructure.output.jpa.adapters;

import com.emazon.cart.application.dtos.ProductDto;
import com.emazon.cart.infraestructure.output.jpa.feign.StockFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.emazon.cart.util.TestConstants.*;
import static com.emazon.cart.util.TestConstants.VALID_LIST_PRODUCTS_IDS;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
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
}