package com.emazon.cart.application.handler;

import com.emazon.cart.application.dtos.ShoppingCartRequest;

public interface IShoppingCartHandler {
    void addProductToShoppingCart(ShoppingCartRequest shoppingCartRequest);
    void removeProductFromShoppingCart(Long productId);
}
