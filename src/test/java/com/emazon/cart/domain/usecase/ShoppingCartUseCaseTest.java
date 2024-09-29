package com.emazon.cart.domain.usecase;

import com.emazon.cart.domain.exeption.*;
import com.emazon.cart.domain.model.*;
import com.emazon.cart.domain.spi.IAuthenticationPersistencePort;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;
import com.emazon.cart.domain.spi.IStockPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.emazon.cart.domain.utils.DomainConstants.ASC;
import static com.emazon.cart.util.TestConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ShoppingCartUseCaseTest {
    ShoppingCart shoppingCart;
    @Mock
    private IShoppingCartPersistencePort shoppingCartPersistencePort;
    @Mock
    private IAuthenticationPersistencePort authenticationPersistencePort;
    @Mock
    private IStockPersistencePort productPersistencePort;
    @InjectMocks
    private ShoppingCartUseCase shoppingCartUseCase;

    public static Product getProduct(Long idProduct) {
        return new Product(idProduct, VALID_PRODUCT_NAME, VALID_AMOUNT, null, VALID_PRICE,
                new Brand(VALID_ID, VALID_BRAND_NAME),
                new ArrayList<>(Arrays.asList(new Category(VALID_ID, VALID_CATEGORY_NAME))));
    }

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart(null, null, VALID_ID_PRODUCT, VALID_AMOUNT);
        MockitoAnnotations.openMocks(this);
        when(this.authenticationPersistencePort.getUserId()).thenReturn(VALID_ID);
    }

    @Test
    @DisplayName("Should save product from the shopping cart and verify that the persistence port method is called once")
    void saveShoppingCart() {
        prepareForSaveShoppingCart(TRUE, null);
        this.shoppingCartUseCase.addProductToShoppingCart(shoppingCart);

        ArgumentCaptor<ShoppingCart> shoppingCartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);

        verify(shoppingCartPersistencePort, times(ONE)).save(shoppingCartCaptor.capture());
        assertEquals(shoppingCartCaptor.getValue(), shoppingCart);
    }

    @Test
    @DisplayName("Should save product from the shopping cart and verify that the persistence port method is called once")
    void saveShoppingCartWhenShoppingCartExist() {
        shoppingCart.setId(VALID_ID);
        prepareForSaveShoppingCart(TRUE, shoppingCart);
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
        assertThrowsAndVerifyRestockDay(LocalDate.now().plusDays(ONE).getDayOfMonth());

        reset(shoppingCartPersistencePort);

        assertThrowsAndVerifyRestockDay(LocalDate.now().minusDays(ONE).getDayOfMonth());
    }

    private void assertThrowsAndVerifyRestockDay(int dayOfMonth) {
        when(shoppingCartPersistencePort.getRestockDay()).thenReturn(dayOfMonth);
        assertThrows(InsufficientStockException.class,
                () -> shoppingCartUseCase.addProductToShoppingCart(shoppingCart)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }

    @Test
    @DisplayName("Should not save the shoppingCart when the max categories exceeded")
    void shouldNotSaveShoppingCartWhenMaxCategoriesExceeded() {
        prepareForSaveShoppingCart(FALSE, null);
        assertThrows(MaxProductsPerCategoryException.class,
                () -> shoppingCartUseCase.addProductToShoppingCart(shoppingCart)
        );
        verify(shoppingCartPersistencePort, never()).save(shoppingCart);
    }

    void prepareForSaveShoppingCart(boolean validateMax, ShoppingCart shopping) {
        when(this.productPersistencePort.getAmountByIdProduct(shoppingCart.getIdProduct())).thenReturn(VALID_AMOUNT);
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

    @Test
    @DisplayName("Should  return paginated products in shopping cart")
    void shouldGetPaginatedProductsInShoppingCart() {

        Product product = getProduct(VALID_ID_PRODUCT);
        PageShopping<Product> productPageShopping = new PageShopping<>(
                List.of(product, getProduct(VALID_ID_SECOND)), VALID_TOTAL_ELEMENTS, VALID_TOTAL_PAGES, true, true, VALID_SIZE
        );
        List<ShoppingCart> shoppingCartList = Arrays.asList(new ShoppingCart(VALID_ID, VALID_ID, VALID_ID_PRODUCT, VALID_AMOUNT), new ShoppingCart(VALID_ID, VALID_ID, VALID_ID_SECOND, VALID_AMOUNT_RESTOCK));
        when(this.authenticationPersistencePort.getUserId()).thenReturn(VALID_ID);
        when(this.shoppingCartPersistencePort.getProductIds(VALID_ID)).thenReturn(VALID_LIST_PRODUCT_ID);
        when(this.productPersistencePort.getPaginatedProductsInShoppingCart(VALID_LIST_PRODUCT_ID, BRAND_FILTER, CATEGORY_FILTER, ASC, VALID_PAGE, VALID_SIZE))
                .thenReturn(productPageShopping);
        when(this.shoppingCartPersistencePort.getRestockDay()).thenReturn(RESTOCK_DAY);
        when(this.shoppingCartPersistencePort.getShoppingCartListByIdProductInAndUserId(VALID_ID, VALID_LIST_PRODUCT_ID))
                .thenReturn(shoppingCartList);

        PageShopping<Product> result = shoppingCartUseCase.getPaginatedProductsInShoppingCart(BRAND_FILTER, CATEGORY_FILTER, ASC, VALID_PAGE, VALID_SIZE);
        assertEquals(productPageShopping, result);
    }

    @Test
    @DisplayName("Should  return paginated empty products in shopping cart")
    void shouldGetPaginatedEmptyProductsInShoppingCart() {

        when(this.shoppingCartPersistencePort.getProductIds(VALID_ID)).thenReturn(List.of());

        assertEmptyPageShopping();

        reset(this.shoppingCartPersistencePort);

        assertEmptyPageShopping();

        when(this.shoppingCartPersistencePort.getProductIds(VALID_ID)).thenReturn(VALID_LIST_PRODUCT_ID);
        when(this.productPersistencePort.getPaginatedProductsInShoppingCart(VALID_LIST_PRODUCT_ID, BRAND_FILTER, CATEGORY_FILTER, ASC, VALID_PAGE, VALID_SIZE))
                .thenThrow(new RuntimeException());

        assertEmptyPageShopping();
    }

    private void assertEmptyPageShopping() {
        when(this.authenticationPersistencePort.getUserId()).thenReturn(VALID_ID);
        when(this.shoppingCartPersistencePort.getRestockDay()).thenReturn(RESTOCK_DAY);
        PageShopping<Product> result = shoppingCartUseCase.getPaginatedProductsInShoppingCart(BRAND_FILTER, CATEGORY_FILTER, ASC, VALID_PAGE, VALID_SIZE);
        assert (result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isEqualTo(ZERO);
        assertThat(result.getTotalPages()).isEqualTo(ZERO);
        assertThat(result.isFirst()).isTrue();
        assertThat(result.isLast()).isTrue();
        assertThat(result.getPageSize()).isEqualTo(ZERO);
    }

    @Test
    @DisplayName("Should not return paginated products in shopping cart when page size is invalid")
    void shouldNotGetPaginatedProductsInShoppingCartWhenPageSizeIsInvalid() {
        assertThrows(ShoppingCartPageSizeIsInvalidException.class,
                () -> shoppingCartUseCase.getPaginatedProductsInShoppingCart(BRAND_FILTER, CATEGORY_FILTER, ASC, VALID_PAGE, INVALID_SIZE)
        );
    }

    @Test
    @DisplayName("Should not return paginated products in shopping cart when page number is invalid")
    void shouldNotGetPaginatedProductsInShoppingCartWhenPageNumberIsInvalid() {
        assertThrows(ShoppingCartPageNumberIsInvalidException.class, () -> shoppingCartUseCase.getPaginatedProductsInShoppingCart(BRAND_FILTER, CATEGORY_FILTER, ASC, INVALID_PAGE, VALID_SIZE));
    }

    @Test
    @DisplayName("Should not return paginated products in shopping cart when sort direction is invalid")
    void shouldNotGetPaginatedProductsInShoppingCartWhenSortDirectionIsInvalid() {
        assertThrows(ShoppingCartPageSortDirectionIsInvalidException.class,
                () -> shoppingCartUseCase.getPaginatedProductsInShoppingCart(BRAND_FILTER, CATEGORY_FILTER, INVALID_SORT_DIRECTION, VALID_PAGE, VALID_SIZE)
        );
    }

    @Test
    @DisplayName("Should not return paginated products in shopping cart when FILTER is invalid")
    void shouldNotGetPaginatedProductsInShoppingCartWhenFilterIsInvalid() {
        assertThrows(ProductFilterNotFoundException.class,
                () -> shoppingCartUseCase.getPaginatedProductsInShoppingCart(null, null, ASC, VALID_PAGE, VALID_SIZE)
        );
    }
}