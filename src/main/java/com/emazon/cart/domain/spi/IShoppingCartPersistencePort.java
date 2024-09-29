package com.emazon.cart.domain.spi;

import com.emazon.cart.domain.model.ShoppingCart;

import java.util.List;

public interface IShoppingCartPersistencePort {
    ShoppingCart findByIdUserAndIdProduct(Long idUser, Long idProduct);

    void save(ShoppingCart shoppingCart);

    List<ShoppingCart> findByIdUserAndIdProductIn(Long idUser, List<Long> idList);

    List<Long> getProductIds(Long id);

    int getRestockDay();

    List<ShoppingCart> getShoppingCartListByIdProductInAndUserId(Long id, List<Long> idList);

    void saveAll(List<ShoppingCart> shoppingCartList);

    Integer countByUserId(Long userId);
}
