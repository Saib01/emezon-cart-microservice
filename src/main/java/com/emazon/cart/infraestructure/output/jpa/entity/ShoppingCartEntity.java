package com.emazon.cart.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.emazon.cart.infraestructure.util.InfrastructureConstants.*;


@Entity
@Table(name = TABLE_SHOPPING_CART_ENTITY)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_SHOPPING_CART, nullable = false)
    private Long id;
    private Long idUser;
    private Long idProduct;
    private Integer amount;
    @Column(name = COLUMN_CREATED_DATE, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;
    @Column(name = COLUMN_UPDATE_DATE)
    @LastModifiedDate
    private LocalDateTime updateDate;
}