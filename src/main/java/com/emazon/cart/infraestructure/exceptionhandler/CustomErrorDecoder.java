package com.emazon.cart.infraestructure.exceptionhandler;


import com.emazon.cart.domain.exeption.ProductNotFoundException;
import com.emazon.cart.infraestructure.exception.UnknownException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.emazon.cart.domain.exeption.ExceptionResponse.PRODUCT_NOT_FOUND;
import static com.emazon.cart.domain.exeption.ExceptionResponse.UNKNOWN_ERROR;
import static com.emazon.cart.infraestructure.util.InfrastructureConstants.PRODUCT;

@Slf4j
@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        log.info("Error Decoder:{},{}", methodKey, response);
        if (methodKey.toLowerCase().contains(PRODUCT.toLowerCase())) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        return new UnknownException(UNKNOWN_ERROR);
    }
}
