package aviation.services;

import aviation.models.Airport;
import aviation.models.dto.AirportDto;
import aviation.repositories.AirportRepository;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;

@Singleton
@Slf4j
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Flux<AirportDto> getAirports() {
        List<Airport> airports = airportRepository.findAll();
        log.info("AIRPORT NAME {}", airports.get(0).getAirportId());
        return Flux.fromIterable(airports)
                .map(this::toDto);
    }

    private AirportDto toDto(Airport airport) {
        return new AirportDto(airport.getId(),
                airport.getGmt(),
                airport.getAirportId(),
                airport.getIataCode(),
                airport.getCityIataCode(),
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
