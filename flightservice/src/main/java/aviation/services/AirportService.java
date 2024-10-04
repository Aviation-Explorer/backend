package aviation.services;

import aviation.models.airport.Airport;
import aviation.models.airport.dto.AirportDto;
import aviation.models.city.City;
import aviation.repositories.AirportRepository;
import aviation.repositories.CityRepository;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Singleton
@Slf4j
public class AirportService {
  private final AirportRepository airportRepository;
  private final CityRepository cityRepository;

  public AirportService(AirportRepository airportRepository, CityRepository cityRepository) {
    this.airportRepository = airportRepository;
    this.cityRepository = cityRepository;
  }

  public Flux<AirportDto> getAirports() {
    List<Airport> airports = airportRepository.findAll();
    return Flux.fromIterable(airports).map(this::toDto);
  }

  private AirportDto toDto(Airport airport) {
    Optional<City> city = cityRepository.findByIataCode(airport.getCityIataCode());

    return new AirportDto(
        airport.getId(),
        airport.getGmt(),
        airport.getAirportId(),
        airport.getIataCode(),
        airport.getCityIataCode(),
        city.map(City::getCityName).orElse(null),
        airport.getIcaoCode(),
        airport.getCountryIso2(),
        airport.getGeonameId(),
        airport.getLatitude(),
        airport.getLongitude(),
        airport.getAirportName(),
        airport.getPhoneNumber(),
        airport.getCountryName(),
        airport.getTimezone());
  }
}
