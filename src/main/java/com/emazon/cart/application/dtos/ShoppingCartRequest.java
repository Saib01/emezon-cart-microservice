package com.emazon.cart.application.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartRequest {
    private Integer amount;
    private Long idProduct;

    public ShoppingCartRequest(Integer amount, Long idProduct) {
        this.amount = amount;
        this.idProduct = idProduct;
    }
}
