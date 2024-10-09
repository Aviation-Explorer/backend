package aviation.models.dto;

import aviation.models.AviationUserFlight;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record FlightSubmissionDto(String email, AviationUserFlight flight) {}
