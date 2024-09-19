package com.emazon.cart.domain.api;

import com.emazon.cart.domain.model.ShoppingCart;

public interface IShoppingCartServicePort {
    void addProductToShoppingCart(ShoppingCart shoppingCart);
}
