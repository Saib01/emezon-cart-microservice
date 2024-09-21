package com.emazon.cart.infraestructure.output.jpa.mapper;


import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.infraestructure.output.jpa.entity.ShoppingCartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ShoppingCartEntityMapper {
    ShoppingCart toShoppingCart(ShoppingCartEntity shoppingCartEntity);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    ShoppingCartEntity toShoppingCartEntity(ShoppingCart shoppingCart);
}
