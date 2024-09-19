package com.emazon.cart.infraestructure.documentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class OpenApiConfiguration {
    private static final String TITTLE = "Hexagonal Monolithic MicroserviceShoppingCart API";
    private static final String VERSION = "1.0";
    private static final String DESCRIPTION = "MicroService ShoppingCart";
    private static final String TERMS_OF_SERVICE = "http://swagger.io/terms/";
    private static final String APACHE = "Apache 2.0";
    private static final String URL_SPRING_DOC = "http://springdoc.org";

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(TITTLE)
                        .version(VERSION)
                        .description(DESCRIPTION)
                        .termsOfService(TERMS_OF_SERVICE)
                        .license(new License().name(APACHE).url(URL_SPRING_DOC)));
    }
}
