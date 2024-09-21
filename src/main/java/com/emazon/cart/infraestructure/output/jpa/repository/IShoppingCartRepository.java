package com.emazon.cart.infraestructure.output.jpa.repository;

import com.emazon.cart.infraestructure.output.jpa.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
    Optional<List<ShoppingCartEntity>> findByIdUserAndAmountGreaterThan(Long idUser, int amount);

    Optional<ShoppingCartEntity> findByIdUserAndIdProduct(Long userId, Long productId);
}