package aviation.models.stats;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UserFlightStat(
    String airline,
    Long count
) {
    
}
