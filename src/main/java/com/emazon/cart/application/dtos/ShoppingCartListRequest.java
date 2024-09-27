package com.emazon.cart.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartListRequest {
    private List<Long> listIdsProducts;
    private String categoryName;
    private String brandName;
}
