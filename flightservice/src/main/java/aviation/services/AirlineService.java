package aviation.services;

import aviation.models.airline.Airline;
import aviation.models.airline.dto.AirlineDto;
import aviation.repositories.AirlineRepository;
import jakarta.inject.Singleton;
import java.util.List;
import reactor.core.publisher.Flux;

@Singleton
public class AirlineService {
  private final AirlineRepository airlineRepository;

  public AirlineService(AirlineRepository airlineRepository) {
    this.airlineRepository = airlineRepository;
  }

  public Flux<AirlineDto> getAirlines() {
    List<Airline> airlines = airlineRepository.findAll();

    return Flux.fromIterable(airlines).map(this::toDto);
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
