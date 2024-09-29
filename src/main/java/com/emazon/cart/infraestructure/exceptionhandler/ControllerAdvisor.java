package com.emazon.cart.infraestructure.exceptionhandler;

import com.emazon.cart.domain.exeption.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.util.Collections;
import java.util.Map;


@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "Response";


    private ResponseEntity<Map<String, String>> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(Collections.singletonMap(MESSAGE, message));
    }

    @ExceptionHandler({
            AmountIsInvalidException.class,
            ProductIdIsInvalidException.class,
            ShoppingCartPageSortDirectionIsInvalidException.class,
            ShoppingCartPageNumberIsInvalidException.class,
            ShoppingCartPageSizeIsInvalidException.class,
            ShoppingCartIdIsInvalidException.class
    })
    public ResponseEntity<Map<String, String>> handleBadRequestExceptions(RuntimeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
            MaxProductsPerCategoryException.class,
            DuplicateShoppingCartIdException.class
    })
    public ResponseEntity<Map<String, String>> handleConflictExceptions(RuntimeException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler({
            InsufficientStockException.class
    })
    public ResponseEntity<Map<String, String>> handleNotAcceptableExceptions(RuntimeException ex) {
        return buildResponse(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
    }

    @ExceptionHandler({
            ProductNotFoundException.class,
            ProductFilterNotFoundException.class,
            ShoppingCartNotFoundException.class
    })
    public ResponseEntity<Map<String, String>> handleNotFOUNDExceptions(RuntimeException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({
            ConnectException.class
    })
    public ResponseEntity<Map<String, String>> handleInternalServerExceptions(RuntimeException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
