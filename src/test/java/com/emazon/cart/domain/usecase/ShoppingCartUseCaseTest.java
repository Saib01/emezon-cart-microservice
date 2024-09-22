package com.emazon.cart.domain.usecase;

import com.emazon.cart.domain.exeption.AmountIsInvalidException;
import com.emazon.cart.domain.exeption.InsufficientStockException;
import com.emazon.cart.domain.exeption.MaxProductsPerCategoryException;
import com.emazon.cart.domain.exeption.ProductIdIsInvalidException;
import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.emazon.cart.util.TestConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ShoppingCartUseCaseTest {
    ShoppingCart shoppingCart;
    @Mock
    private IShoppingCartPersistencePort shoppingCartPersistencePort;
    @InjectMocks
    private ShoppingCartUseCase shoppingCartUseCase;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart(getNull(), getNull(), VALID_ID_PRODUCT, VALID_AMOUNT);
        MockitoAnnotations.openMocks(this);
        when(this.shoppingCartPersistencePort.getUserId()).thenReturn(VALID_ID);
    }

    @Test
    @DisplayName("Should save product from the shopping cart and verify that the persistence port method is called once")
    void saveShoppingCart() {
        prepareForSaveShoppingCart(TRUE);
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
    @DisplayName("Should not save the shoppingCart when the stock is insufficient")
    void shouldNotSaveShoppingCartWhenStockIsInsufficient() {
        prepareAmountByIdProduct(INSUFFICIENT_STOCK);
        assertThrows(InsufficientStockException.class,
                () -> shoppingCartUseCase.addProductToShoppingCart(shoppingCart)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }

    @Test
    @DisplayName("Should not save the shoppingCart when the max categories exceeded")
    void shouldNotSaveShoppingCartWhenMaxCategoriesExceeded() {
        prepareForSaveShoppingCart(FALSE);
        assertThrows(MaxProductsPerCategoryException.class,
                () -> shoppingCartUseCase.addProductToShoppingCart(shoppingCart)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }

    void prepareAmountByIdProduct(Integer amount) {
        when(this.shoppingCartPersistencePort.getAmountByIdProduct(shoppingCart.getIdProduct())).thenReturn(amount);
    }

    void prepareForSaveShoppingCart(boolean validateMax) {
        prepareAmountByIdProduct(VALID_AMOUNT);
        when(this.shoppingCartPersistencePort.getAmountByIdProduct(shoppingCart.getIdProduct())).thenReturn(VALID_AMOUNT);
        when(this.shoppingCartPersistencePort.findByIdUserAndIdProduct(shoppingCart.getIdUser(), shoppingCart.getIdProduct())).thenReturn(getNull());
        when(this.shoppingCartPersistencePort.getProductIds(VALID_ID)).thenReturn(VALID_LIST_PRODUCTS_IDS);
        when(this.shoppingCartPersistencePort.validateMaxProductPerCategory(anyList())).thenReturn(validateMax);
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
        assertThrows(ProductIdIsInvalidException.class,
                () -> shoppingCartUseCase.removeProductFromShoppingCart(VALID_ID_PRODUCT)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }
    @Test
    @DisplayName("Should not remove product when amount is zero")
    void shouldNotRemoveProductWhenAmountIsZero() {
        when(this.shoppingCartPersistencePort.findByIdUserAndIdProduct(VALID_ID, VALID_ID_PRODUCT)).thenReturn(shoppingCart);
        shoppingCart.setIdUser(VALID_ID);
        shoppingCart.setAmount(0);

        assertThrows(ProductIdIsInvalidException.class,
                () -> shoppingCartUseCase.removeProductFromShoppingCart(VALID_ID_PRODUCT)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }

}