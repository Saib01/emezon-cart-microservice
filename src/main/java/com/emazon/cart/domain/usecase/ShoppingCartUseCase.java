package com.emazon.cart.domain.usecase;

import com.emazon.cart.domain.api.IShoppingCartServicePort;
import com.emazon.cart.domain.exeption.ProductNotFoundException;
import com.emazon.cart.domain.model.PageShopping;
import com.emazon.cart.domain.model.Product;
import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.domain.spi.IAuthenticationPersistencePort;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;
import com.emazon.cart.domain.spi.IStockPersistencePort;
import com.emazon.cart.domain.utils.ShoppingCartValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.emazon.cart.domain.exeption.ExceptionResponse.PRODUCT_NOT_FOUND;
import static com.emazon.cart.domain.utils.ShoppingCartValidator.messageForInsufficientStock;
import static com.emazon.cart.domain.utils.ShoppingCartValidator.validateIdProduct;
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

    private static PageShopping<Product> getResponsePageShoppingEmpty() {
        return new PageShopping<>(
                List.of(), ZERO, ZERO, true, true, ZERO
        );
    }

    @Override
    public void addProductToShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setIdUser(this.authenticationPersistencePort.getUserId());
        ShoppingCartValidator.addProductToShoppingCart(shoppingCart, this.shoppingCartPersistencePort, this.productPersistencePort);
        this.shoppingCartPersistencePort.save(shoppingCart);
    }

    @Override
    public void removeProductFromShoppingCart(Long productId) {
        validateIdProduct(productId);
        ShoppingCart shoppingCart = shoppingCartPersistencePort.findByIdUserAndIdProduct(
                this.authenticationPersistencePort.getUserId(), productId);
        if (shoppingCart == null || shoppingCart.getAmount().equals(ZERO)) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        shoppingCart.setAmount(ZERO);
        this.shoppingCartPersistencePort.save(shoppingCart);
    }

    @Override
    public PageShopping<Product> getPaginatedProductsInShoppingCart(String brandName, String categoryName, String sortDirection, int page, int size) {
        ShoppingCartValidator.getPaginatedProductsInShoppingCart(sortDirection, page, size, brandName, categoryName);

        Long userId = this.authenticationPersistencePort.getUserId();

        List<Long> productIds = new ArrayList<>(
                shoppingCartPersistencePort.getProductIds(userId)
        );

        PageShopping<Product> productResponsePageShopping = getPaginatedProductsInShoppingCart(brandName, categoryName, sortDirection, page, size, productIds);

        productIds = productResponsePageShopping.getContent().stream().map(Product::getId).toList();

        productResponsePageShopping.setTotal(
                getTotal(productResponsePageShopping,
                        userId, productIds)
        );

        return productResponsePageShopping;
    }

    private Double getTotal(PageShopping<Product> productPageShopping, Long userId, List<Long> productIds) {
        String restockDay = messageForInsufficientStock(shoppingCartPersistencePort.getRestockDay());
        AtomicReference<Double> total = new AtomicReference<>(ZERO.doubleValue());
        Map<Long, Integer> unitsInCart = this.shoppingCartPersistencePort.getShoppingCartListByIdProductInAndUserId(userId, productIds).stream()
                .collect(Collectors.toMap(ShoppingCart::getIdProduct, ShoppingCart::getAmount));
        productPageShopping.getContent().forEach(product -> {
            product.setUnitsInCart(unitsInCart.get(product.getId()));
            if (product.getUnitsInCart() > product.getAmount()) {
                product.setRestockDate(restockDay);
            } else {
                total.updateAndGet(v -> v + product.getPrice() * product.getAmount());
            }
        });
        return total.get();
    }

    private PageShopping<Product> getPaginatedProductsInShoppingCart(
            String brandName, String categoryName, String sortDirection, int page, int size, List<Long> productIds
    ) {
        try {
            return productIds.isEmpty() ?
                    getResponsePageShoppingEmpty()
                    : this.productPersistencePort
                    .getPaginatedProductsInShoppingCart(productIds, brandName, categoryName, sortDirection, page, size);
        } catch (RuntimeException e) {
            return getResponsePageShoppingEmpty();
        }
    }
}
