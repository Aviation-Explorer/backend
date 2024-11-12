package aviation.controllers;

import aviation.models.airline.dto.AirlineDto;
import aviation.services.AirlineService;
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
public class AirlineController {
  private final AirlineService airlineService;

  public AirlineController(AirlineService airlineService) {
    this.airlineService = airlineService;
  }

  @Get("/airlines")
  public Flux<AirlineDto> getAirlines() {
    return airlineService.getAirlines();
  }
}
