package com.emazon.cart.domain.model;

import java.time.LocalDateTime;

public class ShoppingCart {
    private Long id;
    private Long idUser;
    private Long idProduct;
    private Integer amount;
    private LocalDateTime createdDate;

    public ShoppingCart(Long id, Long idUser, Long idProduct, Integer amount, LocalDateTime createdDate) {
        this.id = id;
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.amount = amount;
        this.createdDate = createdDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
