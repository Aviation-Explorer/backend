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
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.RolesAllowed;
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

  @Operation(summary = "Get all users")
  @RolesAllowed("ADMIN")
  @Get("/users")
  @Produces(MediaType.APPLICATION_JSON)
  public Flux<AviationUserDto> getUsers() {
    return userService.findAll();
  }

  @Operation(summary = "Get one user")
  @Get("/{email}")
  @Secured(SecurityRule.IS_ANONYMOUS)
  public Mono<AviationUserDto> getOneUser(@PathVariable String email) {
    return userService.findOne(email);
  }

  @Operation(summary = "Check credentials")
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Post("/verify")
  public Mono<Boolean> verifyCredentials(@Body UserCredentials credentials) {
    log.info("Verifying credentials for: {}. Invoked from authservice.", credentials.email());
    return userService.verifyCredentials(credentials);
  }

  @Operation(summary = "Update user password")
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Patch("/update-password")
  public Mono<Boolean> updatePassword(@Body UserCredentials credentials) {
    return userService.updatePassword(credentials);
  }

  @Operation(summary = "Create new user")
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

  @Operation(summary = "Assign user to flight")
  @RolesAllowed("USER")
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

  @Operation(summary = "Get flights related to user")
  @Get("/{email}/flights")
  @Produces(MediaType.APPLICATION_JSON)
  public Flux<AviationUserFlightDto> getUserFlights(@PathVariable("email") String email) {
    return userService
        .getFlightsForUser(email)
        .doOnError(e -> log.error("Error fetching flights for user {}: {}", email, e.getMessage()))
        .onErrorResume(e -> Flux.empty());
  }

  @Operation(summary = "Unasign user from flight")
  @RolesAllowed({"USER", "ADMIN"})
  @Delete("/{email}/flight/{id}")
  public Mono<MutableHttpResponse<Object>> deleteFlightForUser(
      @PathVariable("email") String email, @PathVariable("id") Long id) {
    return userService
        .deleteFlightForUser(email, id)
        .then(Mono.just(HttpResponse.noContent()))
        .onErrorResume(
            IllegalArgumentException.class,
            e -> {
              log.error("Error deleting flight for user {}: {}", email, e.getMessage());
              return Mono.just(HttpResponse.status(HttpStatus.NOT_FOUND, e.getMessage()));
            });
  }

  @Operation(summary = "Delete user")
  @RolesAllowed("ADMIN")
  @Delete("/{email}")
  public Mono<MutableHttpResponse<Object>> deleteUser(
      @PathVariable("email") String email) {
    return userService.deleteUser(email)
        .then(Mono.just(HttpResponse.noContent()))
        .onErrorResume(IllegalAccessError.class,
            e -> {
              log.error("Error deleting user {}: {}", email, e.getMessage());
              return Mono.just(HttpResponse.status(HttpStatus.NOT_FOUND, e.getMessage()));
            });
  }

  @Operation(summary = "Block or unblock user")
  @RolesAllowed("ADMIN")
  @Patch("/{email}/status")
  public Mono<Boolean> updateUserStatus(
      @PathVariable("email") String email,
      @Body("toBlock") Boolean toBlock) {
    return userService.updateUserStatus(email, toBlock);
  }
}
