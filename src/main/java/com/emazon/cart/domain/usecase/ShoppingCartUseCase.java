package com.emazon.cart.domain.usecase;

import com.emazon.cart.domain.api.IShoppingCartServicePort;
import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;
import com.emazon.cart.domain.utils.ShoppingCartValidator;

public class ShoppingCartUseCase implements IShoppingCartServicePort {
    private final IShoppingCartPersistencePort shoppingCartPersistencePort;

    public ShoppingCartUseCase(IShoppingCartPersistencePort shoppingCartPersistencePort) {
        this.shoppingCartPersistencePort = shoppingCartPersistencePort;
    }

    @Override
    public void addProductToShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setIdUser(this.shoppingCartPersistencePort.getUserId());
        ShoppingCartValidator.addProductToShoppingCart(shoppingCart, this.shoppingCartPersistencePort);
        this.shoppingCartPersistencePort.save(shoppingCart);
    }
}
