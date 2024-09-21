package com.emazon.cart.domain.model;


public class ShoppingCart {
    private Long id;
    private Long idUser;
    private Long idProduct;
    private Integer amount;

    public ShoppingCart(Long id, Long idUser, Long idProduct, Integer amount) {
        this.id = id;
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.amount = amount;
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
