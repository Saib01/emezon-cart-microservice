package com.emazon.cart.infraestructure.output.jpa.mapper;


import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.infraestructure.output.jpa.entity.ShoppingCartEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ShoppingCartEntityMapper {
    ShoppingCart toShoppingCart(ShoppingCartEntity shoppingCartEntity);

    ShoppingCartEntity toShoppingCartEntity(ShoppingCart shoppingCart);
}
