package com.emazon.cart.application.mapper;


import com.emazon.cart.application.dtos.ShoppingCartRequest;
import com.emazon.cart.domain.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.emazon.cart.application.util.ApplicationConstants.ID;
import static com.emazon.cart.application.util.ApplicationConstants.ID_USER;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ShoppingCartRequestMapper {
    @Mapping(target = ID, ignore = true)
    @Mapping(target = ID_USER, ignore = true)
    ShoppingCart toShoppingCart(ShoppingCartRequest shoppingCartRequest);
}
