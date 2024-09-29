package aviation.filters;

import aviation.models.UserCredentials;
import aviation.services.AviationUserService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.HttpRequestExecutorAuthenticationProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class AuthenticationProviderUserPassword<B> implements HttpRequestExecutorAuthenticationProvider<B> {
    @Inject
    private AviationUserService userService;

    @Override
    public @NonNull AuthenticationResponse authenticate(@Nullable HttpRequest<B> requestContext,
            @NonNull AuthenticationRequest<String, String> authRequest) {        
        UserCredentials credentials = new UserCredentials(authRequest.getIdentity(), authRequest.getSecret());

        Mono<Boolean> areValidCredentials = userService.verifyCredentials(credentials);

        return Boolean.TRUE.equals(areValidCredentials.block())
                ? AuthenticationResponse.success(authRequest.getIdentity())
                : AuthenticationResponse.failure();
    }

}
