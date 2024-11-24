package aviation.controllers;

import aviation.models.stats.UserCreatedStat;
import aviation.models.stats.UserFlightStat;
import aviation.services.AviationUserService;
import aviation.services.FlightUserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.RolesAllowed;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/api/user-stats")
@Secured(SecurityRule.IS_AUTHENTICATED)
@RolesAllowed("ADMIN")
public class FlightUserController {
    private final FlightUserService flightUserService;
    private final AviationUserService aviationUserService;

    public FlightUserController(FlightUserService flightUserService,
            AviationUserService aviationUserService) {
        this.flightUserService = flightUserService;
        this.aviationUserService = aviationUserService;
    }

    @Operation(summary = "Get statistics of user airline relation")
    @Get("/airline")
    public Flux<UserFlightStat> getFlightAirlineStats() {
        return flightUserService.countUsersByAirline();
    }

    @Operation(summary = "Get count of blocked users")
    @Get("/blocked")
    public Mono<Long> getBlockedUsers() {
        return aviationUserService.countBlockedUsers();
    }

    @Operation(summary = "Get count of new users")
    @Get("/created")
    public Flux<UserCreatedStat> getUserCreatedAtStats() {
        return aviationUserService.countCreatedAtUsers();
    }
}
