package aviation.models.flight.dto;

import aviation.models.city.City;
import aviation.models.flight.AirlineFlight;
import aviation.models.flight.Arrival;
import aviation.models.flight.Departure;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record FlightDto(
    String flightDate,
    String flightStatus,
    Departure departure,
    Arrival arrival,
    AirlineFlight airline,
    City departureCity,
    City arrivalCity
) {}
