package com.emazon.cart.domain.spi;

import java.util.List;

public interface IStockPersistencePort {
    Integer getAmountByIdProduct(Long idProduct);

    boolean validateMaxProductPerCategory(List<Long> listIdsProducts);
}
