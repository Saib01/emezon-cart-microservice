package com.emazon.cart.infraestructure.input.rest;

import com.emazon.cart.application.dtos.Response;
import com.emazon.cart.application.dtos.ShoppingCartRequest;
import com.emazon.cart.application.handler.IShoppingCartHandler;
import com.emazon.cart.infraestructure.output.jpa.feign.StockFeignClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emazon.cart.infraestructure.util.InfraestructureRestControllerConstants.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartRestController {

    private final IShoppingCartHandler shoppingCartHandler;
    private final StockFeignClient stockFeignClient;

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

}
