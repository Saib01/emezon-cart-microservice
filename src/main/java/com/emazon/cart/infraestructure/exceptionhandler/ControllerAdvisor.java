package com.emazon.cart.infraestructure.exceptionhandler;

import com.emazon.cart.domain.exeption.AmountIsInvalidException;
import com.emazon.cart.domain.exeption.InsufficientStockException;
import com.emazon.cart.domain.exeption.MaxProductsPerCategoryException;
import com.emazon.cart.domain.exeption.ProductIdIsInvalidException;
import com.emazon.cart.infraestructure.exception.FeignProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.util.Collections;
import java.util.Map;


@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "Message";


    private ResponseEntity<Map<String, String>> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(Collections.singletonMap(MESSAGE, message));
    }

    @ExceptionHandler({
            AmountIsInvalidException.class,
            ProductIdIsInvalidException.class,
    })
    public ResponseEntity<Map<String, String>> handleBadRequestExceptions(RuntimeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
            MaxProductsPerCategoryException.class
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
            FeignProductNotFoundException.class
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
