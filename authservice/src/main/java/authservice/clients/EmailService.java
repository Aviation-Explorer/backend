package authservice.clients;

import authservice.exceptions.EmailNotValidException;
import authservice.exceptions.NoEmailExistsException;
import authservice.exceptions.TokenNotFoundException;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.micronaut.email.BodyType;
import io.micronaut.email.Email;
import io.micronaut.email.EmailSender;
import io.micronaut.email.template.TemplateBody;
import io.micronaut.views.ModelAndView;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Singleton
@Slf4j
public class EmailService {
  private final EmailSender emailSender;
  private final StatefulRedisConnection<String, String> redis;
  private final UserServiceClient userServiceClient;

  private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

  public EmailService(
      EmailSender emailSender,
      StatefulRedisConnection<String, String> redis,
      UserServiceClient userServiceClient) {
    this.emailSender = emailSender;
    this.redis = redis;
    this.userServiceClient = userServiceClient;
  }

  public Mono<CharSequence> send(String receiverEmail) {
    if (!isValidEmail(receiverEmail)) {
      return Mono.error(new EmailNotValidException("Invalid email"));
    }

    return userServiceClient
        .getUserByEmail(receiverEmail)
        .flatMap(
            userResponse -> {
              if (userResponse != null) {
                return Mono.error(
                    new NoEmailExistsException("Email address not found: " + receiverEmail));
              }

              String token = UUID.randomUUID().toString();
              String resetLink = "http://localhost:3000/reset-password/" + token;

              Map<String, Object> model =
                  Map.of("userEmail", receiverEmail, "resetLink", resetLink);
              Email.Builder emailBuilder =
                  Email.builder()
                      .to(receiverEmail)
                      .subject("Password reset request")
                      .body(new TemplateBody<>(BodyType.HTML, new ModelAndView<>("email", model)));

              emailSender.send(emailBuilder);
              saveResetToken(receiverEmail, token);
              return Mono.just(token);
            });
  }

  public Mono<String> getValue(String email) {
    RedisCommands<String, String> redisCommands = redis.sync();
    String tokenToSave = "reset_token" + email;
    String token = redisCommands.get(tokenToSave);

    if (token == null) {
      return Mono.error(new TokenNotFoundException("UUID token not found for email " + email));
    }
    return Mono.just(redisCommands.get(tokenToSave));
  }

  private boolean isValidEmail(String email) {
    return email != null && EMAIL_PATTERN.matcher(email).matches();
  }

  private void saveResetToken(String email, String token) {
    RedisCommands<String, String> redisCommands = redis.sync();
    String tokenToSave = "reset_token" + email;
    redisCommands.set(tokenToSave, token);
    redisCommands.expire(tokenToSave, 60);
  }
}
