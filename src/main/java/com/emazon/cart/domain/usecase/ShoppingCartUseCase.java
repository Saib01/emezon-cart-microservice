package com.emazon.cart.domain.usecase;

import com.emazon.cart.domain.api.IShoppingCartServicePort;
import com.emazon.cart.domain.exeption.ProductIdIsInvalidException;
import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;
import com.emazon.cart.domain.utils.ShoppingCartValidator;

import static com.emazon.cart.domain.exeption.ExceptionResponse.PRODUCT_NOT_FOUND;
import static com.emazon.cart.domain.utils.ShoppingCartValidator.*;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

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

    @Override
    public void removeProductFromShoppingCart(Long productId) {
        validateIdProduct(productId);
        ShoppingCart shoppingCart = shoppingCartPersistencePort.findByIdUserAndIdProduct(
                this.shoppingCartPersistencePort.getUserId(), productId);
        if (shoppingCart == null||shoppingCart.getAmount()==0) {
            throw new ProductIdIsInvalidException(PRODUCT_NOT_FOUND);
        }
        shoppingCart.setAmount(ZERO);
        this.shoppingCartPersistencePort.save(shoppingCart);
    }
}
