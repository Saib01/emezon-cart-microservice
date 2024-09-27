package com.emazon.cart.infraestructure.output.jpa.feign;

import com.emazon.cart.application.dtos.ProductDto;
import com.emazon.cart.application.dtos.ShoppingCartListRequest;
import com.emazon.cart.domain.model.PageShopping;
import com.emazon.cart.domain.model.Product;
import com.emazon.cart.infraestructure.configuration.jwt.FeingClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.emazon.cart.domain.utils.DomainConstants.ASC;
import static com.emazon.cart.infraestructure.util.InfraestructureRestControllerConstants.*;
import static com.emazon.cart.infraestructure.util.InfrastructureConstants.FEIGN_NAME;
import static com.emazon.cart.infraestructure.util.InfrastructureConstants.FEIGN_STOCK_URL;


@FeignClient(name = FEIGN_NAME, url = FEIGN_STOCK_URL, configuration = FeingClientConfig.class)
public interface StockFeignClient {

    @GetMapping("/{id}")
    ProductDto getProductById(@PathVariable Long id);

    @GetMapping("/validate-category-limit")
    boolean validateMaxProductPerCategory(@RequestParam List<Long> listIdsProducts);

    @PostMapping("/product-list")
    PageShopping<Product> getPaginatedProductsInShoppingCart(
            ShoppingCartListRequest shoppingCartListRequest,
            @RequestParam(name = SORT_DIRECTION, defaultValue = ASC) String sortDirection,
            @RequestParam(name = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = SIZE, defaultValue = DEFAULT_PAGE_SIZE) int size);

}
