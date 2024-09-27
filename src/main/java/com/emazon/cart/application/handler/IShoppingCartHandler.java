package com.emazon.cart.application.handler;

import com.emazon.cart.application.dtos.ShoppingCartRequest;
import com.emazon.cart.domain.model.PageShopping;
import com.emazon.cart.domain.model.Product;

public interface IShoppingCartHandler {
    void addProductToShoppingCart(ShoppingCartRequest shoppingCartRequest);

    void removeProductFromShoppingCart(Long productId);

    PageShopping<Product> getPaginatedProductsInShoppingCart(String brandName, String categoryName, String sortDirection, int page, int size);
}
