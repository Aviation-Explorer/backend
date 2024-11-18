package authservice.clients;

import authservice.models.UserCredentials;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class RefreshTokenHandler implements RefreshTokenPersistence {

  private final Map<String, UserCredentials> tokenStore = new HashMap<>();

  @Override
  public void persistToken(RefreshTokenGeneratedEvent event) {
    String refreshToken = event.getRefreshToken();
    String email = event.getAuthentication().getName();

    tokenStore.put(refreshToken, new UserCredentials(email, "N/A"));
    System.out.println("Persisted refresh token for email: " + email);
  }

  @Override
  public Publisher<Authentication> getAuthentication(String refreshToken) {
    Optional<UserCredentials> userCredentialsOpt =
        Optional.ofNullable(tokenStore.get(refreshToken));

    if (userCredentialsOpt.isPresent()) {
      UserCredentials credentials = userCredentialsOpt.get();
      return Mono.just(Authentication.build(credentials.email()));
    }

    return Mono.empty();
  }
}
