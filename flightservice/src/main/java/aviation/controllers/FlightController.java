package aviation.controllers;

import java.util.List;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/api/flight")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class FlightController {
    @Get("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "Flight Service is up and running!";
    }

    @Get("/flights")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getFlights() {
        return List.of("Flight 101", "Flight 202", "Flight 303");
    }
}
