package com.emazon.cart.infraestructure.input.rest;

import com.emazon.cart.application.dtos.Response;
import com.emazon.cart.application.dtos.ShoppingCartRequest;
import com.emazon.cart.application.handler.IShoppingCartHandler;
import com.emazon.cart.domain.model.PageShopping;
import com.emazon.cart.domain.model.Product;
import com.emazon.cart.domain.model.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emazon.cart.domain.utils.DomainConstants.ASC;
import static com.emazon.cart.infraestructure.util.InfraestructureRestControllerConstants.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartRestController {

    private final IShoppingCartHandler shoppingCartHandler;
    private final ObjectMapper objectMapper;

    @Operation(summary = SUMMARY_ADD_PRODUCT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = RESPONSE_DESCRIPTION_ADD_SUCCESSFUL,
                    content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = RESPONSE_CODE_BAD_REQUEST_DESCRIPTION,
                    content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_CONFLICT, description = RESPONSE_CODE_CONFLICT_DESCRIPTION,
                    content = @Content)
    })
    @PostMapping("/add-product")
    public ResponseEntity<Response> addProductToShoppingCart(
            @RequestBody ShoppingCartRequest shoppingCartRequest) {

        shoppingCartHandler.addProductToShoppingCart(shoppingCartRequest);
        return ResponseEntity.ok(new Response(RESPONSE_DESCRIPTION_ADD_SUCCESSFUL));
    }

    @Operation(summary = SUMMARY_REMOVE_PRODUCT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = RESPONSE_DESCRIPTION_REMOVE_SUCCESSFUL,
                    content = @Content(mediaType = APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = RESPONSE_CODE_BAD_REQUEST_DESCRIPTION,
                    content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_CONFLICT, description = RESPONSE_CODE_CONFLICT_DESCRIPTION,
                    content = @Content)
    })
    @DeleteMapping("/remove-product/{productId}")
    public ResponseEntity<Response> removeProductFromShoppingCart(@PathVariable Long productId) {
        shoppingCartHandler.removeProductFromShoppingCart(productId);
        return ResponseEntity.ok(new Response(RESPONSE_DESCRIPTION_REMOVE_SUCCESSFUL));
    }

    @Operation(summary = SUMMARY_GET_PRODUCTS,
            description = RESPONSE_GET_PRODUCTS)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = PRODUCTS_RETRIEVED_SUCCESSFULLY, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_PARAMETERS, content = @Content)
    })
    @GetMapping("/products")
    public ResponseEntity<PageShopping<Product>> getPaginatedProductsInShoppingCart(
            @RequestParam(name = BRAND_NAME, defaultValue = "") String brandName,
            @RequestParam(name = CATEGORY_NAME, defaultValue = "") String categoryName,
            @RequestParam(name = SORT_DIRECTION, defaultValue = ASC) String sortDirection,
            @RequestParam(name = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = SIZE, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return ResponseEntity.ok(
                shoppingCartHandler.getPaginatedProductsInShoppingCart(brandName, categoryName, sortDirection, page, size)
        );
    }

    @Operation(summary = REMOVE_PRODUCTS_FROM_THE_SHOPPING_CART,
            description = REMOVES_PRODUCTS_IDENTIFIED_BY_THEIR_IDS_FROM_THE_SHOPPING_CART)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = PRODUCTS_SUCCESSFULLY_REMOVED_FROM_THE_SHOPPING_CART,
                    content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_OR_MISSING_PRODUCT_ID_LIST, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = ONE_OR_MORE_PRODUCT_IDS_NOT_FOUND_IN_THE_SHOPPING_CART, content = @Content)
    })
    @DeleteMapping("/remove-product")
    public ResponseEntity<Response> removeProductListFromShoppingCart(@RequestParam(name = LIST_ID, defaultValue = "") List<Long> productIdList) {
        shoppingCartHandler.removeProductListFromShoppingCart(productIdList);
        return ResponseEntity.ok(new Response(RESPONSE_DESCRIPTION_REMOVE_LIST_IDS_SUCCESSFUL));

    }

    @Operation(summary = RESTORE_PRODUCTS_IN_THE_SHOPPING_CART,
            description = RESTORES_PRODUCTS_IN_THE_SHOPPING_CART_FROM_A_PROVIDED_LIST_OF_SHOPPING_CART_ITEMS)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = PRODUCTS_SUCCESSFULLY_RESTORED_IN_THE_SHOPPING_CART, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_SHOPPING_CART_LIST, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER, description = INTERNAL_SERVER_ERROR_UNABLE_TO_RESTORE_PRODUCTS, content = @Content)
    })
    @PutMapping("/restore-products-fallback")
    public ResponseEntity<Response> restoreShoppingCartFromShoppingCartList(@RequestBody List<ShoppingCart> shoppingCartList) {
        shoppingCartHandler.restoreShoppingCartFromShoppingCartList(shoppingCartList);
        return ResponseEntity.ok(new Response(PRODUCTS_SUCCESSFULLY_RESTORED));
    }

    @Operation(summary = RETRIEVE_SHOPPING_CART_ITEMS_BY_PRODUCT_IDS,
            description = FETCH_A_LIST_OF_SHOPPING_CART_ITEMS_USING_A_LIST_OF_PRODUCT_IDS)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = SHOPPING_CART_ITEMS_RETRIEVED_SUCCESSFULLY, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_OR_EMPTY_PRODUCT_ID_LIST, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = NO_SHOPPING_CART_ITEMS_FOUND_FOR_THE_PROVIDED_PRODUCT_IDS, content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getListShoppingCartInListIdProduct(@RequestParam(name = LIST_ID, defaultValue = "") List<Long> listProductIds) {
        return ResponseEntity.ok(
                shoppingCartHandler.getListShoppingCartInListIdProduct(listProductIds)
        );
    }

    @Operation(summary = GET_TOTAL_NUMBER_OF_SHOPPING_CART_ITEMS_FOR_THE_USER,
            description = RETRIEVE_THE_TOTAL_NUMBER_OF_ITEMS_IN_THE_SHOPPING_CART_FOR_THE_CURRENT_USER)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = TOTAL_NUMBER_OF_ITEMS_IN_THE_SHOPPING_CART_RETRIEVED_SUCCESSFULLY, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_USER_OR_SHOPPING_CART_DATA, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = SHOPPING_CART_NOT_FOUND_FOR_THE_USER, content = @Content)
    })
    @GetMapping("/total")
    public ResponseEntity<Integer> countByUserId() {
        return ResponseEntity.ok(
                shoppingCartHandler.countByUserId()
        );
    }
}
