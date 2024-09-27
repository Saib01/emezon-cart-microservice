package com.emazon.cart.domain.usecase;

import com.emazon.cart.domain.api.IShoppingCartServicePort;
import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.domain.spi.IAuthenticationPersistencePort;
import com.emazon.cart.domain.spi.IStockPersistencePort;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;
import com.emazon.cart.domain.utils.ShoppingCartValidator;
import com.emazon.cart.domain.exeption.ProductNotFoundException;

import static com.emazon.cart.domain.exeption.ExceptionResponse.PRODUCT_NOT_FOUND;
import static com.emazon.cart.domain.utils.ShoppingCartValidator.*;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

public class ShoppingCartUseCase implements IShoppingCartServicePort {
    private final IShoppingCartPersistencePort shoppingCartPersistencePort;
    private final IStockPersistencePort productPersistencePort;
    private final IAuthenticationPersistencePort authenticationPersistencePort;

    public ShoppingCartUseCase(IShoppingCartPersistencePort shoppingCartPersistencePort, IStockPersistencePort productPersistencePort, IAuthenticationPersistencePort authenticationPersistencePort) {
        this.shoppingCartPersistencePort = shoppingCartPersistencePort;
        this.productPersistencePort = productPersistencePort;
        this.authenticationPersistencePort = authenticationPersistencePort;
    }

    @Override
    public void addProductToShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setIdUser(this.authenticationPersistencePort.getUserId());
        ShoppingCartValidator.addProductToShoppingCart(shoppingCart, this.shoppingCartPersistencePort,this.productPersistencePort);
        this.shoppingCartPersistencePort.save(shoppingCart);
    }

    @Override
    public void removeProductFromShoppingCart(Long productId) {
        validateIdProduct(productId);
        ShoppingCart shoppingCart = shoppingCartPersistencePort.findByIdUserAndIdProduct(
                this.authenticationPersistencePort.getUserId(), productId);
        if (shoppingCart == null||shoppingCart.getAmount().equals(ZERO)) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        shoppingCart.setAmount(ZERO);
        this.shoppingCartPersistencePort.save(shoppingCart);
    }

}
