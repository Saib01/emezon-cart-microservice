package com.emazon.cart.domain.usecase;

import com.emazon.cart.domain.exeption.*;
import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.domain.spi.IAuthenticationPersistencePort;
import com.emazon.cart.domain.spi.IStockPersistencePort;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static com.emazon.cart.util.TestConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ShoppingCartUseCaseTest {
    ShoppingCart shoppingCart;
    @Mock
    private IShoppingCartPersistencePort shoppingCartPersistencePort;
    @Mock
    private IAuthenticationPersistencePort authenticationPersistencePort;
    @Mock
    private IStockPersistencePort productPersistencePort;
    @InjectMocks
    private ShoppingCartUseCase shoppingCartUseCase;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart(null,null, VALID_ID_PRODUCT, VALID_AMOUNT);
        MockitoAnnotations.openMocks(this);
        when(this.authenticationPersistencePort.getUserId()).thenReturn(VALID_ID);
    }

    @Test
    @DisplayName("Should save product from the shopping cart and verify that the persistence port method is called once")
    void saveShoppingCart() {
        prepareForSaveShoppingCart(TRUE,null);
        this.shoppingCartUseCase.addProductToShoppingCart(shoppingCart);

        ArgumentCaptor<ShoppingCart> shoppingCartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);

        verify(shoppingCartPersistencePort, times(ONE)).save(shoppingCartCaptor.capture());
        assertEquals(shoppingCartCaptor.getValue(), shoppingCart);
    }

    @Test
    @DisplayName("Should save product from the shopping cart and verify that the persistence port method is called once")
    void saveShoppingCartWhenShoppingCartExist() {
        shoppingCart.setId(VALID_ID);
        prepareForSaveShoppingCart(TRUE,shoppingCart);
        this.shoppingCartUseCase.addProductToShoppingCart(shoppingCart);

        ArgumentCaptor<ShoppingCart> shoppingCartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);

        verify(shoppingCartPersistencePort, times(ONE)).save(shoppingCartCaptor.capture());
        assertEquals(shoppingCartCaptor.getValue(), shoppingCart);
    }

    @Test
    @DisplayName("Should not save the shoppingCart when the idProduct is invalid")
    void shouldNotSaveShoppingCartWhenIdProductIsInvalid() {
        shoppingCart.setIdProduct(INVALID_ID_PRODUCT);
        assertThrows(ProductIdIsInvalidException.class,
                () -> shoppingCartUseCase.addProductToShoppingCart(shoppingCart)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }

    @Test
    @DisplayName("Should not save the shoppingCart when the amount is invalid")
    void shouldNotSaveShoppingCartWhenAmountIsInvalid() {
        shoppingCart.setAmount(INVALID_AMOUNT);
        assertThrows(AmountIsInvalidException.class,
                () -> shoppingCartUseCase.addProductToShoppingCart(shoppingCart)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }

    @Test
    @DisplayName("Should not save the shoppingCart when the stock is insufficient based on restock day")
    void shouldNotSaveShoppingCartWhenStockIsInsufficientBasedOnRestockDay() {
        prepareAmountByIdProduct(INSUFFICIENT_STOCK);

        when(shoppingCartPersistencePort.getRestockDay()).thenReturn(LocalDate.now().plusDays(ONE).getDayOfMonth());
        assertThrows(InsufficientStockException.class,
                () -> shoppingCartUseCase.addProductToShoppingCart(shoppingCart)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);

        reset(shoppingCartPersistencePort);

        when(shoppingCartPersistencePort.getRestockDay()).thenReturn(LocalDate.now().minusDays(ONE).getDayOfMonth());
        assertThrows(InsufficientStockException.class,
                () -> shoppingCartUseCase.addProductToShoppingCart(shoppingCart)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }

    @Test
    @DisplayName("Should not save the shoppingCart when the max categories exceeded")
    void shouldNotSaveShoppingCartWhenMaxCategoriesExceeded() {
        prepareForSaveShoppingCart(FALSE,null);
        assertThrows(MaxProductsPerCategoryException.class,
                () -> shoppingCartUseCase.addProductToShoppingCart(shoppingCart)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }

    void prepareAmountByIdProduct(Integer amount) {
        when(this.productPersistencePort.getAmountByIdProduct(shoppingCart.getIdProduct())).thenReturn(amount);
    }

    void prepareForSaveShoppingCart(boolean validateMax,ShoppingCart shopping) {
        prepareAmountByIdProduct(VALID_AMOUNT);
        when(this.shoppingCartPersistencePort.findByIdUserAndIdProduct(anyLong(), anyLong())).thenReturn(shopping);
        when(this.shoppingCartPersistencePort.getProductIds(VALID_ID)).thenReturn(VALID_LIST_PRODUCTS_IDS);
        when(this.productPersistencePort.validateMaxProductPerCategory(anyList())).thenReturn(validateMax);
    }

    @Test
    @DisplayName("Should remove the product from the shopping cart and verify that the persistence port method is called once")
    void removeShoppingCart() {
        when(this.shoppingCartPersistencePort.findByIdUserAndIdProduct(VALID_ID, VALID_ID_PRODUCT)).thenReturn(shoppingCart);
        shoppingCart.setId(VALID_ID);
        shoppingCart.setIdUser(VALID_ID);

        shoppingCartUseCase.removeProductFromShoppingCart(VALID_ID_PRODUCT);

        ArgumentCaptor<ShoppingCart> shoppingCartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);

        verify(shoppingCartPersistencePort, times(ONE)).save(shoppingCartCaptor.capture());
        assertEquals(shoppingCartCaptor.getValue(), shoppingCart);
    }

    @Test
    @DisplayName("It should not remove when the product does not exist in the Shopping cart")
    void shouldNotRemoveProductWhenItDoesNotExistInCart() {
        when(this.shoppingCartPersistencePort.findByIdUserAndIdProduct(VALID_ID, VALID_ID_PRODUCT)).thenReturn(null);
        shoppingCart.setIdUser(VALID_ID);
        assertThrows(ProductNotFoundException.class,
                () -> shoppingCartUseCase.removeProductFromShoppingCart(VALID_ID_PRODUCT)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }
    @Test
    @DisplayName("Should not remove product when amount is zero")
    void shouldNotRemoveProductWhenAmountIsZero() {
        when(this.shoppingCartPersistencePort.findByIdUserAndIdProduct(VALID_ID, VALID_ID_PRODUCT)).thenReturn(shoppingCart);
        shoppingCart.setIdUser(VALID_ID);
        shoppingCart.setAmount(ZERO);

        assertThrows(ProductNotFoundException.class,
                () -> shoppingCartUseCase.removeProductFromShoppingCart(VALID_ID_PRODUCT)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }

}