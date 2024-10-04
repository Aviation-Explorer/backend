package aviation.controllers;

import aviation.models.airport.dto.AirportDto;
import aviation.services.AirportService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Controller("/api/flight-data")
@ExecuteOn(TaskExecutors.BLOCKING)
@Secured(SecurityRule.IS_ANONYMOUS)
@Slf4j
public class AirportController {
  private final AirportService airportService;

  public AirportController(AirportService airportService) {
    this.airportService = airportService;
  }

  @Get("/airports")
  public Flux<AirportDto> getAirports() {
    return airportService.getAirports();
  }
}
