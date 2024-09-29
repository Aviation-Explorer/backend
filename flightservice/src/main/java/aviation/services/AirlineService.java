package aviation.services;

import aviation.models.airline.Airline;
import aviation.models.airline.dto.AirlineDto;
import aviation.models.city.City;
import aviation.models.city.dto.CityDto;
import aviation.repositories.AirlineRepository;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

import java.util.List;

@Singleton
public class AirlineService {
  private final AirlineRepository airlineRepository;

  public AirlineService(AirlineRepository airlineRepository) {
    this.airlineRepository = airlineRepository;
  }

  public Flux<AirlineDto> getAirlines() {
    List<Airline> cities = airlineRepository.findAll();

    return Flux.fromIterable(cities).map(this::toDto);
  }

  private AirlineDto toDto(Airline airline) {
    return new AirlineDto(
        airline.getId(),
        airline.getFleetAverageAge(),
        airline.getAirlineId(),
        airline.getCallsign(),
        airline.getHubCode(),
        airline.getIataCode(),
        airline.getIcaoCode(),
        airline.getCountryIso2(),
        airline.getDateFounded(),
        airline.getIataPrefixAccounting(),
        airline.getAirlineName(),
        airline.getCountryName(),
        airline.getFleetSize(),
        airline.getStatus(),
        airline.getType());
  }
}
