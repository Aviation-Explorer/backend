package authservice.services;

import io.micronaut.context.annotation.Requires;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.token.jwt.validator.JsonWebTokenValidator;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.net.http.HttpResponse;
import java.util.Optional;

import authservice.clients.UserServiceClient;
import authservice.models.UserCredentials;
import java.util.logging.Logger;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Singleton
@Requires(bean = JsonWebTokenValidator.class)
@SerdeImport(value = SignedJWT.class)
@SerdeImport(value = Payload.class)
@SerdeImport(value = Base64URL.class)
@SerdeImport(value = JWSHeader.class)
@SerdeImport(value = JWSAlgorithm.class)
@SerdeImport(value = JWTClaimsSet.class)
public class JWTService {

    @Inject
    private TokenGenerator tokenGenerator;
    @Inject
    private UserServiceClient userServiceClient;
    @Inject
    private JsonWebTokenValidator jwtValidator;

    private static final Logger LOGGER = Logger.getLogger("JwtRedisAuthService");    

    public Mono<String> authenticateUser(UsernamePasswordCredentials authentication, Integer expiration) {
        String userEmail = authentication.getUsername();
        String userPassword = authentication.getPassword();

        UserCredentials credentials = new UserCredentials(userEmail, userPassword);

        return userServiceClient.verifyCredentials(credentials)
                .flatMap(isValid -> {
                    if (isValid) {
                        Authentication auth = Authentication.build(userEmail);

                        Optional<String> tokenOptional = tokenGenerator.generateToken(auth, expiration);

                        return tokenOptional.map(Mono::just).orElse(Mono.empty());
                    } else {
                        return Mono.empty();
                    }
                });

    }

    public Optional<String> validateToken(String token) {    
        if(token.startsWith("Bearer ")) {
            token.substring(7, token.length());
        }

        return jwtValidator.validate(token, null);

    }
}
