package aviation.models.dto;

import io.micronaut.serde.annotation.Serdeable;

import java.sql.Date;

@Serdeable
public record AviationUserFlightDto(
    Long id,
    String airline,
    String aviationUserEmail,
    String departureAirport,
    String arrivalAirport,
    Date flightDate,
    String departureTerminal,
    String departureGate,
    String arrivalTerminal,
    String arrivalGate) {}
