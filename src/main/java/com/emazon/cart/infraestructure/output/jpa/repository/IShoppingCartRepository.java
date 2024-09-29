package com.emazon.cart.infraestructure.output.jpa.repository;

import com.emazon.cart.infraestructure.output.jpa.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
    Optional<List<ShoppingCartEntity>> findByIdUserAndAmountGreaterThan(Long idUser, int amount);

    Optional<ShoppingCartEntity> findByIdUserAndIdProduct(Long idUser, Long productId);

    Optional<List<ShoppingCartEntity>> findByIdUserAndIdProductIn(Long idUser, List<Long> idList);

    Optional<List<ShoppingCartEntity>> findByIdUserAndIdProductInAndAmountGreaterThan(Long idUser, List<Long> idList, int amount);

    @Query("SELECT SUM(s.amount) FROM ShoppingCartEntity s WHERE s.idUser = :idUser AND s.amount > :amount")
    Integer sumAmountByIdUserAndAmountGreaterThan(@Param("idUser") Long idUser, @Param("amount") int amount);
}