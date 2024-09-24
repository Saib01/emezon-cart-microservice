package com.emazon.cart.infraestructure.output.jpa.adapters;

import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.infraestructure.output.jpa.entity.ShoppingCartEntity;
import com.emazon.cart.infraestructure.output.jpa.mapper.ShoppingCartEntityMapper;
import com.emazon.cart.infraestructure.output.jpa.repository.IShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.emazon.cart.util.TestConstants.*;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartJpaAdapterTest {

    @InjectMocks
    private ShoppingCartJpaAdapter shoppingCartJpaAdapter;

    @Mock
    private IShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartEntityMapper shoppingCartEntityMapper;
    private ShoppingCart shoppingCart;
    private ShoppingCartEntity shoppingCartEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingCart = new ShoppingCart(VALID_ID, VALID_ID, VALID_ID_PRODUCT, VALID_AMOUNT);
        shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity.setIdProduct(shoppingCart.getIdProduct());
        shoppingCartEntity.setId(shoppingCart.getId());
        shoppingCartEntity.setIdUser(shoppingCart.getIdUser());
        shoppingCartEntity.setAmount(shoppingCart.getAmount());
    }

    @Test
    @DisplayName("Should return ShoppingCart when finding by user ID and product ID")
    void shouldReturnShoppingCartWhenFindingByIdUserAndIdProduct() {
        when(shoppingCartRepository.findByIdUserAndIdProduct(VALID_ID, VALID_ID_PRODUCT))
                .thenReturn(Optional.of(shoppingCartEntity));
        when(shoppingCartEntityMapper.toShoppingCart(shoppingCartEntity))
                .thenReturn(shoppingCart);

        ShoppingCart result = shoppingCartJpaAdapter.findByIdUserAndIdProduct(VALID_ID, VALID_ID_PRODUCT);

        assertNotNull(result);
        assertEquals(shoppingCart, result);
        verify(shoppingCartRepository).findByIdUserAndIdProduct(VALID_ID, VALID_ID_PRODUCT);
        verify(shoppingCartEntityMapper).toShoppingCart(shoppingCartEntity);
    }


    @Test
    @DisplayName("Should return list of product IDs for the user")
    void shouldReturnProductIdsForUser() {
        List<ShoppingCartEntity> cartEntities = VALID_LIST_PRODUCTS_IDS
                .stream()
                .map(idProduct ->
                        {
                            ShoppingCartEntity cartEntity = new ShoppingCartEntity();
                            cartEntity.setIdProduct(idProduct);
                            return cartEntity;
                        }
                ).toList();

        when(shoppingCartRepository.findByIdUserAndAmountGreaterThan(VALID_ID, ZERO))
                .thenReturn(Optional.of(cartEntities));

        List<Long> result = shoppingCartJpaAdapter.getProductIds(VALID_ID);
        assertEquals(VALID_LIST_PRODUCTS_IDS, result);
    }

    @Test
    @DisplayName("Should save shopping cart")
    void shouldSaveShoppingCart() {

        when(shoppingCartEntityMapper.toShoppingCartEntity(shoppingCart))
                .thenReturn(shoppingCartEntity);

        shoppingCartJpaAdapter.save(shoppingCart);

        verify(shoppingCartRepository).save(shoppingCartEntity);
    }
}