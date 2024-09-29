package aviation.models.airline.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record AirlineDto(
    String id,
    String fleetAverageAge,
    String airlineId,
    String callsign,
    String hubCode,
    String iataCode,
    String icaoCode,
    String countryIso2,
    String dateFounded,
    String iataPrefixAccounting,
    String airlineName,
    String countryName,
    String fleetSize,
    String status,
    String type) {}
