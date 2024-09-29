package com.emazon.cart.application.handler.implement;

import com.emazon.cart.application.dtos.ShoppingCartRequest;
import com.emazon.cart.application.handler.IShoppingCartHandler;
import com.emazon.cart.application.mapper.ShoppingCartRequestMapper;
import com.emazon.cart.domain.api.IShoppingCartServicePort;
import com.emazon.cart.domain.model.PageShopping;
import com.emazon.cart.domain.model.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartHandler implements IShoppingCartHandler {
    private final IShoppingCartServicePort shoppingCartServicePort;
    private final ShoppingCartRequestMapper shoppingCartRequestMapper;

    @Override
    public void addProductToShoppingCart(ShoppingCartRequest shoppingCartRequest) {
        shoppingCartServicePort.addProductToShoppingCart(
                shoppingCartRequestMapper.toShoppingCart(shoppingCartRequest)
        );
    }

    @Override
    public void removeProductFromShoppingCart(Long productId) {
        shoppingCartServicePort.removeProductFromShoppingCart(productId);
    }

    @Override
    public PageShopping<Product> getPaginatedProductsInShoppingCart(String brandName, String categoryName, String sortDirection, int page, int size) {
        return shoppingCartServicePort.getPaginatedProductsInShoppingCart(brandName, categoryName, sortDirection, page, size);
    }
}
