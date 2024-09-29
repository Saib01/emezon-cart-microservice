package com.emazon.cart.infraestructure.output.jpa.adapters;

import com.emazon.cart.domain.model.ShoppingCart;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;
import com.emazon.cart.infraestructure.output.jpa.entity.ShoppingCartEntity;
import com.emazon.cart.infraestructure.output.jpa.mapper.ShoppingCartEntityMapper;
import com.emazon.cart.infraestructure.output.jpa.repository.IShoppingCartRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.emazon.cart.infraestructure.util.InfrastructureConstants.RESTOCK_DAY;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

@Getter
@RequiredArgsConstructor
public class ShoppingCartJpaAdapter implements IShoppingCartPersistencePort {
    private final IShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartEntityMapper shoppingCartEntityMapper;
    @Value(RESTOCK_DAY)
    private int restockDay;

    @Override
    public ShoppingCart findByIdUserAndIdProduct(Long idUser, Long idProduct) {
        return this.shoppingCartEntityMapper.toShoppingCart(
                this.shoppingCartRepository.findByIdUserAndIdProduct(idUser, idProduct).orElse(null)
        );
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
    public List<ShoppingCart> getShoppingCartListByIdProductInAndUserId(Long idUser, List<Long> idList) {
        return this.shoppingCartEntityMapper.toShoppingCartList(
                this.shoppingCartRepository.findByIdUserAndIdProductInAndAmountGreaterThan(idUser, idList, ZERO).orElse(List.of())
        );
    }

}
