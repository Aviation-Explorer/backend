package aviation.services;

import aviation.models.country.Country;
import aviation.models.country.dto.CountryDto;
import aviation.repositories.CountryRepository;
import jakarta.inject.Singleton;
import java.util.List;
import reactor.core.publisher.Flux;

@Singleton
public class CountryService {
  private final CountryRepository countryRepository;

  public CountryService(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }

  public Flux<CountryDto> getCountries() {
    List<Country> cities = countryRepository.findAll();

    return Flux.fromIterable(cities).map(this::toDto);
  }

  private CountryDto toDto(Country country) {
    return new CountryDto(
        country.getId(),
        country.getCapital(),
        country.getCurrencyCode(),
        country.getFipsCode(),
        country.getCountryIso2(),
        country.getCountryIso3(),
        country.getContinent(),
        country.getCountryId(),
        country.getCountryId(),
        country.getCurrencyName(),
        country.getCountryIsoNumeric(),
        country.getPhonePrefix(),
        country.getPopulation());
  }
}
