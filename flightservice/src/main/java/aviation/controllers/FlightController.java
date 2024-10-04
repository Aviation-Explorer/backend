package aviation.controllers;

import aviation.models.flight.Flight;
import aviation.services.FlightService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
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
public class FlightController {
  private final FlightService flightService;

  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }

  @Get("/flights")
  public Flux<Flight> getFlights() {
    return flightService.getFlights();
  }

  @Get("/arrivals/{iataAirport}")
  public Flux<Flight> getArrivalsByIataAirport(@PathVariable("iataAirport") String iataAirport) {
    return flightService.getArrivalsByIataAirport(iataAirport);
  }

  @Get("/departures/{iataAirport}")
  public Flux<Flight> getDeparturesByIataAirport(@PathVariable("iataAirport") String iataAirport) {
    return flightService.getDeparturesByIataAirport(iataAirport);
  }
}
