package com.emazon.cart.infraestructure.output.jpa.mapper;


import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.infraestructure.output.jpa.entity.ShoppingCartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static com.emazon.cart.infraestructure.util.InfrastructureConstants.CREATED_DATE;
import static com.emazon.cart.infraestructure.util.InfrastructureConstants.UPDATE_DATE;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ShoppingCartEntityMapper {

    ShoppingCart toShoppingCart(ShoppingCartEntity shoppingCartEntity);

    @Mapping(target = CREATED_DATE, ignore = true)
    @Mapping(target = UPDATE_DATE, ignore = true)
    ShoppingCartEntity toShoppingCartEntity(ShoppingCart shoppingCart);

    List<ShoppingCart> toShoppingCartList(List<ShoppingCartEntity> shoppingCartEntitiesList);
}
