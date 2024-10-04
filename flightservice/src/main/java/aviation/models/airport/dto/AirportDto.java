package aviation.models.airport.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record AirportDto(
    String id,
    String gmt,
    String airportId,
    String iataCode,
    String cityIataCode,
    String cityName,
    String icaoCode,
    String countryIso2,
    String geonameId,
    String latitude,
    String longitude,
    String airportName,
    String phoneNumber,
    String countryName,
    String timezone) {}
