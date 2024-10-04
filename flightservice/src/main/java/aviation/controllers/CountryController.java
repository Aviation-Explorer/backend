package aviation.controllers;

import aviation.models.country.dto.CountryDto;
import aviation.services.CountryService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import reactor.core.publisher.Flux;

@Controller("/api/flight-data")
@ExecuteOn(TaskExecutors.BLOCKING)
@Secured(SecurityRule.IS_ANONYMOUS)
public class CountryController {
  private final CountryService countryService;

  public CountryController(CountryService countryService) {
    this.countryService = countryService;
  }

  @Get("/countries")
  public Flux<CountryDto> getCountries() {
    return countryService.getCountries();
  }
}
