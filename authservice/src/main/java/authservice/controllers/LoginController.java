package authservice.controllers;

import java.util.Optional;
import java.util.logging.Logger;

import authservice.services.JWTService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Head;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/api/auth")
public class LoginController {
    private static final Logger LOG = Logger.getLogger("LoginController");
    private static final int EXPIRATION_TIME = 30;

    @Inject
    private JWTService jwtRedisAuthService;

    
    @Post("/login")
    public Mono<String> login(@Body UsernamePasswordCredentials credentials) {                 
        return jwtRedisAuthService.authenticateUser(credentials, EXPIRATION_TIME)
        .flatMap(token -> {
            if(token != null) {
                LOG.info("Logged in successfully with token: " + token);
                return Mono.just(token);
            } else {
                LOG.warning("Login failed: invalid credentials");
                return Mono.error(new RuntimeException("Invalid credentials"));
            }
        });        
    }

    @Get("/validate")
    public Optional<String> validateToken(@Header String jwtToken) {
        return jwtRedisAuthService.validateToken(jwtToken);
    }
    
    @Post("/logout")
    public void logout(String token) {        
        LOG.info("Logged out successfully");
    }

}
