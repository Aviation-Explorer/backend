package aviation.services;

import aviation.models.airport.Airport;
import aviation.models.city.City;
import aviation.models.flight.Flight;
import aviation.models.flight.dto.FlightDto;
import aviation.repositories.AirportRepository;
import aviation.repositories.CityRepository;
import aviation.repositories.FlightRepository;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;
import reactor.core.publisher.Flux;

@Singleton
public class FlightService {
  private final FlightRepository flightRepository;
  private final AirportRepository airportRepository;
  private final CityRepository cityRepository;

  public FlightService(
      FlightRepository flightRepository,
      AirportRepository airportRepository,
      CityRepository cityRepository) {
    this.flightRepository = flightRepository;
    this.airportRepository = airportRepository;
    this.cityRepository = cityRepository;
  }

  public Flux<FlightDto> getFlights(
      String originAirport, String destinationAirport, String flightDate) {
    List<Flight> flights;

    if (originAirport == null && destinationAirport == null && flightDate == null) {
      flights = flightRepository.findAll().stream().toList();
    } else {
      flights =
          flightRepository.findFlightsByParameters(originAirport, destinationAirport, flightDate);
    }

    return Flux.fromIterable(flights).map(this::toDto);
  }

  public Flux<FlightDto> getArrivalsByIataAirport(String iataAirport) {
    return Flux.fromIterable(
        flightRepository.findAll().stream()
            .filter(flight -> flight.getArrival().getIata().equals(iataAirport))
            .map(this::toDto)
            .toList());
  }

  public Flux<FlightDto> getDeparturesByIataAirport(String iataAirport) {
    return Flux.fromIterable(
        flightRepository.findAll().stream()
            .filter(flight -> flight.getDeparture().getIata().equals(iataAirport))
            .map(this::toDto)
            .toList());
  }

  public Flux<FlightDto> getGetFlightsByIataAirport(String iataAirport) {
    return Flux.fromIterable(
        flightRepository.findDeparturesByIataAirport(iataAirport).stream()
            .map(this::toDto)
            .toList());
  }

  private FlightDto toDto(Flight flight) {
    Optional<Airport> departureAirport =
        airportRepository.findByIataCode(flight.getDeparture().getIata());
    Optional<Airport> arrivalAirport =
        airportRepository.findByIataCode(flight.getArrival().getIata());

    Optional<City> arrivalCity = Optional.empty();
    Optional<City> departureCity = Optional.empty();

    if (arrivalAirport.isPresent() && departureAirport.isPresent()) {

      departureCity = cityRepository.findByIataCode(departureAirport.get().getCityIataCode());

      arrivalCity = cityRepository.findByIataCode(arrivalAirport.get().getCityIataCode());
    }

    return new FlightDto(
        flight.get_id(),
        flight.getFlightDate(),
        flight.getFlightStatus(),
        flight.getDeparture(),
        flight.getArrival(),
        flight.getAirlineFlight(),
        departureCity.orElse(null),
        arrivalCity.orElse(null));
  }
}
