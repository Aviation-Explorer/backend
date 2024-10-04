package aviation.controllers;

import aviation.models.city.dto.CityDto;
import aviation.services.CityService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/api/flight-data")
@ExecuteOn(TaskExecutors.BLOCKING)
@Secured(SecurityRule.IS_ANONYMOUS)
public class CityController {
  private final CityService cityService;

  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  @Get("/cities")
  public Flux<CityDto> getCities() {
    return cityService.getCities();
  }

  @Get("/cities/{iataCode}")
  public Mono<CityDto> getCityByIataCode(@PathVariable(value = "iataCode") String iataCode) {
    return cityService.getCityByIataCode(iataCode);
  }
}
