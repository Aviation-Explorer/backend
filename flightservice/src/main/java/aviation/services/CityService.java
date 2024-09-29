package aviation.services;

import aviation.models.city.City;
import aviation.models.city.dto.CityDto;
import aviation.repositories.CityRepository;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

import java.util.List;

@Singleton
public class CityService {
  private final CityRepository cityRepository;

  public CityService(CityRepository cityRepository) {
    this.cityRepository = cityRepository;
  }

  public Flux<CityDto> getCities() {
    List<City> cities = cityRepository.findAll();

    return Flux.fromIterable(cities).map(this::toDto);
  }

  private CityDto toDto(City city) {
    return new CityDto(
        city.getId(),
        city.getGmt(),
        city.getCityId(),
        city.getIataCode(),
        city.getCountryIso2(),
        city.getGeonameId(),
        city.getLatitude(),
        city.getLongitude(),
        city.getCityName(),
        city.getTimezone());
  }
}
