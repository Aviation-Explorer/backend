package authservice.services;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.generator.TokenGenerator;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.Optional;

import authservice.clients.UserServiceClient;
import authservice.models.UserCredentials;
import java.util.logging.Logger;

@Singleton
public class JwtRedisAuthService {

    private final TokenGenerator tokenGenerator;
    private final UserServiceClient userServiceClient;
    private final RedisClient redisClient;
    private static final Logger LOGGER = Logger.getLogger("JwtRedisAuthService");

    public JwtRedisAuthService(
            TokenGenerator tokenGenerator,
            UserServiceClient userServiceClient,
            RedisClient redisClient) {
        this.tokenGenerator = tokenGenerator;
        this.userServiceClient = userServiceClient;
        this.redisClient = redisClient;
    }

    public Mono<String> generateAndStoreToken(UsernamePasswordCredentials authentication, Integer expiration) {
        String userEmail = authentication.getUsername();
        String userPassword = authentication.getPassword();

        UserCredentials credentials = new UserCredentials(userEmail, userPassword);

        return userServiceClient.verifyCredentials(credentials)
                .flatMap(isValid -> {
                    if (isValid) {
                        Authentication auth = Authentication.build(userEmail);

                        Optional<String> tokenOptional = tokenGenerator.generateToken(auth, expiration);

                        tokenOptional.ifPresent(token -> {
                            try (var connection = redisClient.connect()) {
                                RedisCommands<String, String> syncCommands = connection.sync();
                                syncCommands.set(token, authentication.getIdentity());
                                syncCommands.expire(token, expiration);
                            }
                        });
                        return tokenOptional.map(Mono::just).orElse(Mono.empty());
                    } else {
                        return Mono.empty();
                    }
                });

    }

    public Optional<String> retrieveUserFromToken(String token) {
        try (var connection = redisClient.connect()) {
            RedisCommands<String, String> syncCommands = connection.sync();
            return Optional.ofNullable(syncCommands.get(token));
        }
    }

    public void revokeToken(String token) {
        try (var connection = redisClient.connect()) {
            RedisCommands<String, String> syncCommands = connection.sync();
            syncCommands.del(token);
        }
    }
}
