package com.emazon.cart.application.mapper;


import com.emazon.cart.application.dtos.ShoppingCartRequest;
import com.emazon.cart.domain.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ShoppingCartRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idUser", ignore = true)
    ShoppingCart toShoppingCart(ShoppingCartRequest shoppingCartRequest);
}
