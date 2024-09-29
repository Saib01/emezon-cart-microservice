package com.emazon.cart.domain.spi;

import com.emazon.cart.domain.model.PageShopping;
import com.emazon.cart.domain.model.Product;

import java.util.List;

public interface IStockPersistencePort {
    Integer getAmountByIdProduct(Long idProduct);

    boolean validateMaxProductPerCategory(List<Long> listIdsProducts);

    PageShopping<Product> getPaginatedProductsInShoppingCart(List<Long> listIdsProducts, String brandName, String categoryName, String sortDirection, int page, int size);
}
