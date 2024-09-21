package com.emazon.cart.infraestructure.configuration.jwt;

import com.emazon.cart.domain.api.IShoppingCartServicePort;
import com.emazon.cart.domain.spi.IShoppingCartPersistencePort;
import com.emazon.cart.domain.usecase.ShoppingCartUseCase;
import com.emazon.cart.infraestructure.output.jpa.adapters.ShoppingCartJpaAdapter;
import com.emazon.cart.infraestructure.output.jpa.feign.StockFeignClient;
import com.emazon.cart.infraestructure.output.jpa.mapper.ShoppingCartEntityMapper;
import com.emazon.cart.infraestructure.output.jpa.repository.IShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.emazon.cart.infraestructure.util.InfrastructureConstants.BASE_PACKAGE_FEIGN;

@Configuration
@RequiredArgsConstructor
@EnableFeignClients(basePackages = BASE_PACKAGE_FEIGN)
public class BeanConfiguration {

    private final IShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartEntityMapper shoppingCartEntityMapper;
    private final StockFeignClient stockFeignClient;

    @Bean
    IShoppingCartPersistencePort categoryPersistencePort() {
        return new ShoppingCartJpaAdapter(stockFeignClient, shoppingCartRepository, shoppingCartEntityMapper);
    }

    @Bean
    public IShoppingCartServicePort categoryServicePort() {
        return new ShoppingCartUseCase(categoryPersistencePort());
    }


}
