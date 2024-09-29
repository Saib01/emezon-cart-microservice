package com.emazon.cart.infraestructure.configuration.jwt.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.emazon.cart.domain.exeption.ExceptionResponse.JWT_INVALID;
import static com.emazon.cart.infraestructure.util.InfrastructureConstants.JWT_KEY_GENERATOR;
import static com.emazon.cart.infraestructure.util.InfrastructureConstants.JWT_USER_GENERATOR;

@Component
public class JwtUtils {
    @Value(JWT_KEY_GENERATOR)
    private String privateKey;
    @Value(JWT_USER_GENERATOR)
    private String userGenerator;

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(JWT_INVALID.getMessage());
        }
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }
}