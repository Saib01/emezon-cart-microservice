package com.emazon.cart.infraestructure.output.jpa.feign;

import com.emazon.cart.application.dtos.ProductDto;
import com.emazon.cart.infraestructure.configuration.jwt.FeingClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.emazon.cart.infraestructure.util.InfrastructureConstants.FEIGN_NAME;
import static com.emazon.cart.infraestructure.util.InfrastructureConstants.FEIGN_STOCK_URL;


@FeignClient(name = FEIGN_NAME, url = FEIGN_STOCK_URL, configuration = FeingClientConfig.class)
public interface StockFeignClient {

    @GetMapping("/product/{id}")
    ProductDto getProductById(@PathVariable Long id);

    @GetMapping("/product/validate-category-limit")
    boolean validateMaxProductPerCategory(@RequestParam List<Long> listIdsProducts);

}
