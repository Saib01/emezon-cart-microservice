package com.emazon.cart.application.mapper;

import com.emazon.cart.application.dtos.ShoppingCartRequest;
import com.emazon.cart.domain.model.ShoppingCart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.emazon.cart.util.TestConstants.VALID_AMOUNT;
import static com.emazon.cart.util.TestConstants.VALID_ID_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;

class ShoppingCartRequestMapperTest {
    private final ShoppingCartRequestMapper shoppingCartRequestMapper = Mappers.getMapper(ShoppingCartRequestMapper.class);

    @Test
    @DisplayName("Should map ShoppingCartRequest to ShoppingCarT correctly")
    void shouldMapShoppingCartRequestToShoppingCart() {
        ShoppingCartRequest shoppingCartRequest = new ShoppingCartRequest(VALID_AMOUNT, VALID_ID_PRODUCT);
        ShoppingCart result = shoppingCartRequestMapper.toShoppingCart(shoppingCartRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getIdUser()).isNull();
        assertThat(result.getIdProduct()).isEqualTo(shoppingCartRequest.getIdProduct());
        assertThat(result.getAmount()).isEqualTo(shoppingCartRequest.getAmount());

    }
}