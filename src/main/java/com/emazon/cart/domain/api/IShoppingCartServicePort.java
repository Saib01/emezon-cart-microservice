package com.emazon.cart.domain.api;

import com.emazon.cart.domain.model.PageShopping;
import com.emazon.cart.domain.model.Product;
import com.emazon.cart.domain.model.ShoppingCart;

import java.util.List;

public interface IShoppingCartServicePort {
    void addProductToShoppingCart(ShoppingCart shoppingCart);

    void removeProductFromShoppingCart(Long productId);

    PageShopping<Product> getPaginatedProductsInShoppingCart(String brandName, String categoryName, String sortDirection, int page, int size);

    void removeProductListFromShoppingCart(List<Long> productIdList);

    List<ShoppingCart> getListShoppingCartInListIdProduct(List<Long> listProductIds);


    void restoreShoppingCartFromShoppingCartList(List<ShoppingCart> shoppingCartList);

    Integer countByUserId();
}
