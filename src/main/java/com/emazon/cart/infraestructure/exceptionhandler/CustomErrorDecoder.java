package com.emazon.cart.infraestructure.exceptionhandler;


import com.emazon.cart.infraestructure.exception.FeignProductNotFoundException;
import com.emazon.cart.infraestructure.exception.UnknownException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import static com.emazon.cart.domain.exeption.ExceptionResponse.PRODUCT_NOT_FOUND;
import static com.emazon.cart.domain.exeption.ExceptionResponse.UNKNOWN_ERROR;
import static com.emazon.cart.infraestructure.util.InfrastructureConstants.PRODUCT;


@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (methodKey.toLowerCase().contains(PRODUCT.toLowerCase())) {
            throw new FeignProductNotFoundException(PRODUCT_NOT_FOUND);
        }
        return new UnknownException(UNKNOWN_ERROR);
    }
}
