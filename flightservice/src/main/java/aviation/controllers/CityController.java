package aviation.controllers;

import aviation.models.city.dto.CityDto;
import aviation.services.CityService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import reactor.core.publisher.Flux;

@Controller("/api/flight-data")
@ExecuteOn(TaskExecutors.BLOCKING)
@Secured(SecurityRule.IS_AUTHENTICATED)
public class CityController {
  private final CityService cityService;

  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  @Get("/cities")
  public Flux<CityDto> getCities() {
    return cityService.getCities();
  }
}
