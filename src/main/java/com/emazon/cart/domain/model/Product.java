package com.emazon.cart.domain.model;

import com.emazon.cart.domain.utils.ModelBase;

import java.util.List;


public class Product extends ModelBase {


    private Integer unitsInCart;
    private Integer amount;
    private String restockDate;
    private Double price;
    private Brand brandResponse;
    private List<Category> categoryResponseList;

    public Product(Long id, String name, Integer amount, String restockDate, Double price, Brand brandResponse, List<Category> categoryResponseList) {
        super(id, name);
        this.amount = amount;
        this.restockDate = restockDate;
        this.price = price;
        this.brandResponse = brandResponse;
        this.categoryResponseList = categoryResponseList;
    }

    public Integer getUnitsInCart() {
        return unitsInCart;
    }

    public void setUnitsInCart(Integer unitsInCart) {
        this.unitsInCart = unitsInCart;
    }

    public String getRestockDate() {
        return restockDate;
    }

    public void setRestockDate(String restockDate) {
        this.restockDate = restockDate;
    }

    public List<Category> getCategoryResponseList() {
        return categoryResponseList;
    }

    public void setCategoryResponseList(List<Category> categoryResponseList) {
        this.categoryResponseList = categoryResponseList;
    }

    public Brand getBrandResponse() {
        return brandResponse;
    }

    public void setBrandResponse(Brand brandResponse) {
        this.brandResponse = brandResponse;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}