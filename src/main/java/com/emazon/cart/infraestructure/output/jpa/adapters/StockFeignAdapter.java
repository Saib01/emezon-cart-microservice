package com.emazon.cart.infraestructure.output.jpa.adapters;

import com.emazon.cart.domain.spi.IStockPersistencePort;
import com.emazon.cart.infraestructure.output.jpa.feign.StockFeignClient;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
}
