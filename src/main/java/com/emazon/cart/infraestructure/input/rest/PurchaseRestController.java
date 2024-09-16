package com.emazon.cart.infraestructure.input.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/purchase")
@RequiredArgsConstructor
public class PurchaseRestController {

    @PostMapping
    public ResponseEntity<Void> createPurchase() {
        return ResponseEntity.ok().build();
    }

}
