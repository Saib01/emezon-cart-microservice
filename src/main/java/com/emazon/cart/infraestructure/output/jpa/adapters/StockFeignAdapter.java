package com.emazon.cart.infraestructure.output.jpa.adapters;

import com.emazon.cart.application.dtos.ShoppingCartListRequest;
import com.emazon.cart.domain.model.PageShopping;
import com.emazon.cart.domain.model.Product;
import com.emazon.cart.domain.spi.IStockPersistencePort;
import com.emazon.cart.infraestructure.exceptionhandler.ConnectionRefused;
import com.emazon.cart.infraestructure.output.jpa.feign.StockFeignClient;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;


@RequiredArgsConstructor
public class StockFeignAdapter implements IStockPersistencePort {
    private final StockFeignClient stockFeignClient;

    @Override
    public Integer getAmountByIdProduct(Long idProduct) {
        return this.stockFeignClient.getProductById(idProduct).getAmount();
    }

    @Override
    public boolean validateMaxProductPerCategory(List<Long> listIdsProducts) {
        return this.stockFeignClient.validateMaxProductPerCategory(listIdsProducts);
    }

    @Override
    public PageShopping<Product> getPaginatedProductsInShoppingCart(List<Long> listIdsProducts, String brandName, String categoryName, String sortDirection, int page, int size) {
        try {
            return this.stockFeignClient.getPaginatedProductsInShoppingCart(
                    new ShoppingCartListRequest(listIdsProducts, categoryName, brandName),
                    sortDirection,
                    page,
                    size
            );
        } catch (RuntimeException e) {
            ConnectionRefused.throwIfConnectionRefused(e.getMessage());
            return new PageShopping<>(
                    List.of(), ZERO, ZERO, true, true, ZERO
            );
        }

    }
}
