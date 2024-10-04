package aviation.services;

import aviation.models.flight.Flight;
import aviation.repositories.FlightRepository;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

@Singleton
public class FlightService {
  private final FlightRepository flightRepository;

  public FlightService(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

  public Flux<Flight> getFlights() {
    return Flux.fromIterable(flightRepository.findAll());
  }

  public Flux<Flight> getArrivalsByIataAirport(String iataAirport) {
    return Flux.fromIterable(
        flightRepository.findAll().stream()
            .filter(flight -> flight.getArrival().getIata().equals(iataAirport))
            .toList());
  }

  public Flux<Flight> getDeparturesByIataAirport(String iataAirport) {
    return Flux.fromIterable(
            flightRepository.findAll().stream()
                    .filter(flight -> flight.getDeparture().getIata().equals(iataAirport))
                    .toList());
  }
}
