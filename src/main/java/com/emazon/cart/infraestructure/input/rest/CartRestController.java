package com.emazon.cart.infraestructure.input.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartRestController {

    @PostMapping("/{cartId}/add-item")
    public ResponseEntity<Void> addItemToCart() {
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{cartId}/remove-item/{itemId}")
    public ResponseEntity<Void> removeItemFromCart() {
        return ResponseEntity.ok().build();
    }
}
