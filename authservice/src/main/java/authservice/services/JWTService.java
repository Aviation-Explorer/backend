package authservice.services;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.token.jwt.validator.ReactiveJsonWebTokenValidator;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import authservice.clients.UserServiceClient;
import authservice.models.UserCredentials;

@Singleton
@SerdeImport(value = com.nimbusds.jwt.SignedJWT.class)
@SerdeImport(value = com.nimbusds.jose.Payload.class)
@SerdeImport(value = com.nimbusds.jose.util.Base64URL.class)
@SerdeImport(value = com.nimbusds.jose.JWSHeader.class)
@SerdeImport(value = com.nimbusds.jose.JWSAlgorithm.class)
@SerdeImport(value = com.nimbusds.jwt.JWTClaimsSet.class)
@Slf4j
public class JWTService {

    @Inject
    private TokenGenerator tokenGenerator;
    @Inject
    private UserServiceClient userServiceClient;
    @Inject
    private ReactiveJsonWebTokenValidator<?,HttpRequest<?>> jwtValidator;

    public Mono<String> authenticateUser(UsernamePasswordCredentials authentication, Integer expiration) {
        String userEmail = authentication.getUsername();
        String userPassword = authentication.getPassword();

        UserCredentials credentials = new UserCredentials(userEmail, userPassword);

        return userServiceClient.verifyCredentials(credentials)
                .flatMap(isValid -> {
                    if (isValid) {
                        Authentication auth = Authentication.build(userEmail);

                        return tokenGenerator.generateToken(auth, expiration)
                                .map(Mono::just)
                                .orElse(Mono.empty());
                    } else {
                        return Mono.empty();
                    }
                });
    }

    public Mono<MutableHttpResponse<String>> validateToken(String token, HttpRequest<?> request) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        log.info("token");

        return Mono.from(jwtValidator.validate(token, request))
                .map(validToken -> HttpResponse.ok("Token is valid"))
                .onErrorResume(e -> {
                    log.error("Token validation failed: {}", e.getMessage());
                    return Mono.just(HttpResponse.status(HttpStatus.FORBIDDEN, "Invalid token"));
                });
    }
}
