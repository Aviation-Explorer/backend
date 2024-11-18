package authservice.clients;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Controller("/api/email")
@Secured(SecurityRule.IS_ANONYMOUS)
@Slf4j
public class EmailController {
  private final EmailService emailService;

  public EmailController(EmailService emailService) {
    this.emailService = emailService;
  }

  @Operation(summary = "Send email with password reset link to user email")
  @Get("/send-reset")
  public Mono<MutableHttpResponse<Object>> sendResetEmail(
      @Parameter("receiverEmail") String receiverEmail) {
    return emailService
        .send(receiverEmail)
        .map(token -> HttpResponse.ok().header("email", receiverEmail).header("token", token))
        .onErrorResume(
            IllegalArgumentException.class,
            e -> {
              log.error("Error sending email: {}", e.getMessage());
              return Mono.just(HttpResponse.badRequest("Invalid email address: " + e.getMessage()));
            });
  }

  @Operation(summary = "Get password reset key from Redis by user email")
  @Get
  public Mono<MutableHttpResponse<String>> getValue(@Parameter("email") String email) {
    return emailService
        .getValue(email)
        .map(HttpResponse::ok)
        .onErrorResume(
            IllegalArgumentException.class,
            e -> Mono.just(HttpResponse.status(HttpStatus.CONFLICT, e.getMessage())));
  }
}
