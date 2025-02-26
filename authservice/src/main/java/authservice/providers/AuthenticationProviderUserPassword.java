package authservice.providers;

import authservice.clients.UserServiceClient;
import authservice.exceptions.EmailNotValidException;
import authservice.models.AviationUserDto;
import authservice.models.UserCredentials;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.discovery.exceptions.NoAvailableServiceException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.HttpRequestExecutorAuthenticationProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Singleton
@Slf4j
public class AuthenticationProviderUserPassword<B>
    implements HttpRequestExecutorAuthenticationProvider<B> {
  @Inject
  private UserServiceClient client;

  @Override
  public @NonNull AuthenticationResponse authenticate(
      @Nullable HttpRequest<B> requestContext,
      @NonNull AuthenticationRequest<String, String> authRequest) {
    UserCredentials credentials = new UserCredentials(authRequest.getIdentity(), authRequest.getSecret());

    Mono<HttpResponse<Boolean>> responseMono = client.verifyCredentials(credentials);

    HttpResponse<Boolean> response = responseMono.block();
    log.info("Is validated {}", response.getStatus().getCode());
    try {
      if (response != null && response.getStatus().getCode() == 200 && Boolean.TRUE.equals(response.body())) {

        HttpResponse<AviationUserDto> block = client.getUserByEmail(authRequest.getIdentity()).block();
        if (block.body().isBlocked()) {
          return AuthenticationResponse.failure(AuthenticationFailureReason.ACCOUNT_LOCKED);
        }
        String role = block.body().role();
        return AuthenticationResponse.success(
            authRequest.getIdentity(), Collections.singletonList(role));
      }
      return AuthenticationResponse.failure("Invalid credentials");
    } catch (NoAvailableServiceException e) {
      return AuthenticationResponse.failure(e.getMessage());
    }
  }
}
