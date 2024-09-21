package com.emazon.cart.infraestructure.output.jpa.adapters;

import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;
import com.emazon.cart.infraestructure.output.jpa.entity.ShoppingCartEntity;
import com.emazon.cart.infraestructure.output.jpa.feign.StockFeignClient;
import com.emazon.cart.infraestructure.output.jpa.mapper.ShoppingCartEntityMapper;
import com.emazon.cart.infraestructure.output.jpa.repository.IShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

@RequiredArgsConstructor
public class ShoppingCartJpaAdapter implements IShoppingCartPersistencePort {
    private final StockFeignClient stockFeignClient;
    private final IShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartEntityMapper shoppingCartEntityMapper;

    @Override
    public ShoppingCart findByIdUserAndIdProduct(Long idUser, Long idProduct) {
        return this.shoppingCartEntityMapper.toShoppingCart(
                this.shoppingCartRepository.findByIdUserAndIdProduct(idUser, idProduct).orElse(null)
        );
    }

    @Override
    public Integer getAmountByIdProduct(Long idProduct) {
        return this.stockFeignClient.getProductById(idProduct).getAmount();
    }

    @Override
    public boolean validateMaxProductPerCategory(List<Long> listIdsProducts) {
        return this.stockFeignClient.validateMaxProductPerCategory(listIdsProducts);
    }

    @Override
    public List<Long> getProductIds(Long id) {
        return this.shoppingCartRepository.findByIdUserAndAmountGreaterThan(id, ZERO)
                .orElse(List.of())
                .stream()
                .map(ShoppingCartEntity::getIdProduct)
                .toList();
    }

    @Override
    public void save(ShoppingCart shoppingCart) {
        this.shoppingCartRepository.save(
                this.shoppingCartEntityMapper.toShoppingCartEntity(shoppingCart)
        );
    }

    @Override
    public Long getUserId() {
        return Long.valueOf(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()
                        .toString()
        );
    }
}
