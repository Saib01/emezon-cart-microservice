package com.emazon.cart.application.handler;

import com.emazon.cart.application.dtos.ShoppingCartRequest;
import com.emazon.cart.domain.model.PageShopping;
import com.emazon.cart.domain.model.Product;
import com.emazon.cart.domain.model.ShoppingCart;

import java.util.List;

public interface IShoppingCartHandler {
    void addProductToShoppingCart(ShoppingCartRequest shoppingCartRequest);

    void removeProductFromShoppingCart(Long productId);

    PageShopping<Product> getPaginatedProductsInShoppingCart(String brandName, String categoryName, String sortDirection, int page, int size);

    void removeProductListFromShoppingCart(List<Long> productIdList);

    List<ShoppingCart> getListShoppingCartInListIdProduct(List<Long> listProductIds);


    void restoreShoppingCartFromShoppingCartList(List<ShoppingCart> shoppingCartList);

    Integer countByUserId();
}
