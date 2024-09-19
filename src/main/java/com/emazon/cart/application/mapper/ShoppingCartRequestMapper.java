package com.emazon.cart.application.mapper;


import com.emazon.cart.application.dtos.ShoppingCartRequest;
import com.emazon.cart.domain.model.ShoppingCart;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ShoppingCartRequestMapper {
    ShoppingCart toShoppingCart(ShoppingCartRequest shoppingCartRequest);
}
