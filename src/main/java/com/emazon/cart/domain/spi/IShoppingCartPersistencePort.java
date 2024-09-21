package com.emazon.cart.domain.spi;

import com.emazon.cart.domain.model.ShoppingCart;

import java.util.List;

public interface IShoppingCartPersistencePort {
    ShoppingCart findByIdUserAndIdProduct(Long idUser, Long idProduct);

    Integer getAmountByIdProduct(Long idProduct);

    boolean validateMaxProductPerCategory(List<Long> listIdsProducts);

    List<Long> getProductIds(Long id);

    void save(ShoppingCart shoppingCart);

    Long getUserId();
}
