package com.emazon.cart.application.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto {
    private Integer amount;
    private List<Long> categoryIdsList;
}
