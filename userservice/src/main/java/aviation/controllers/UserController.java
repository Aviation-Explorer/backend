package aviation.controllers;

import aviation.models.AviationUser;
import aviation.models.AviationUserFlight;
import aviation.models.UserCredentials;
import aviation.models.dto.AviationUserDto;
import aviation.models.dto.AviationUserFlightDto;
import aviation.models.dto.FlightSubmissionDto;
import aviation.services.AviationUserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExecuteOn(TaskExecutors.BLOCKING)
@Secured(SecurityRule.IS_AUTHENTICATED)
@Slf4j
@Controller("/api/user")
public class UserController {

  private final AviationUserService userService;

  public UserController(AviationUserService userService) {
    this.userService = userService;
  }

  @Get("/users")
  @Produces(MediaType.APPLICATION_JSON)
  public Flux<AviationUserDto> getUsers() {
    return userService.findAll();
  }

  @Secured(SecurityRule.IS_ANONYMOUS)
  @Post("/verify")
  public Mono<Boolean> verifyCredentials(@Body UserCredentials credentials) {
    log.info("Verifying credentials for: {}. Invoked from authservice.", credentials.email());
    return userService.verifyCredentials(credentials);
  }

  @Post
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Consumes(MediaType.APPLICATION_JSON)
  public Mono<MutableHttpResponse<AviationUserDto>> saveUser(@Body AviationUser user) {
    return userService
        .save(user)
        .map(HttpResponse::created)
        .onErrorResume(
            IllegalArgumentException.class,
            e -> Mono.just(HttpResponse.status(HttpStatus.CONFLICT, e.getMessage())));
  }

  @Post("/flight")
  public Mono<MutableHttpResponse<AviationUserFlight>> saveFlightForUser(
      @Body FlightSubmissionDto submissionDto) {
    return userService
        .saveFlightForUser(submissionDto.email(), submissionDto.flight())
        .map(HttpResponse::created)
        .onErrorResume(
            IllegalArgumentException.class,
            e -> Mono.just(HttpResponse.status(HttpStatus.CONFLICT, e.getMessage())));
  }

  @Get("/{email}/flights")
  @Produces(MediaType.APPLICATION_JSON)
  public Flux<AviationUserFlightDto> getUserFlights(@PathVariable("email") String email) {
    return userService
        .getFlightsForUser(email)
        .doOnError(e -> log.error("Error fetching flights for user {}: {}", email, e.getMessage()))
        .onErrorResume(e -> Flux.empty());
  }

  @Delete("/flight/{email}/{id}")
  public Mono<MutableHttpResponse<?>> deleteFlightForUser(@PathVariable("email") String email, @PathVariable("id") Long id) {
    return userService.deleteFlightForUser(email, id);

  }
}
