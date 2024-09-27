package com.emazon.cart.domain.spi;

import com.emazon.cart.domain.model.ShoppingCart;

import java.util.List;

public interface IShoppingCartPersistencePort {
    ShoppingCart findByIdUserAndIdProduct(Long idUser, Long idProduct);

    void save(ShoppingCart shoppingCart);
    List<Long> getProductIds(Long id);
    int getRestockDay();
}
