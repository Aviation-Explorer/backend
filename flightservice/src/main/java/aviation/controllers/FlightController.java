package aviation.controllers;

import aviation.models.flight.Flight;
import aviation.models.flight.dto.FlightDto;
import aviation.services.FlightService;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
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
  public Flux<FlightDto> getFlights(
          @Nullable @QueryValue(value = "departureIata") String departureIata,
          @Nullable @QueryValue(value = "arrivalIata") String arrivalIata,
          @Nullable @QueryValue(value = "flightDate") String flightDate
          ) {
    return flightService.getFlights(departureIata, arrivalIata, flightDate);
  }

  @Get("/arrivals/{iataAirport}")
  public Flux<FlightDto> getArrivalsByIataAirport(@PathVariable("iataAirport") String iataAirport) {
    return flightService.getArrivalsByIataAirport(iataAirport);
  }

  @Get("/departures/{iataAirport}")
  public Flux<FlightDto> getDeparturesByIataAirport(@PathVariable("iataAirport") String iataAirport) {
    return flightService.getDeparturesByIataAirport(iataAirport);
  }

  @Get("/flights/{iataAirport}")
  public Flux<FlightDto> getGetFlightsByIataAirport(@PathVariable("iataAirport") String iataAirport) {
    return flightService.getGetFlightsByIataAirport(iataAirport);
  }
}
