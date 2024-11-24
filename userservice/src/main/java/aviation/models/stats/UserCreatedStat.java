package aviation.models.stats;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UserCreatedStat(
    String createdat,
    Long count
) {
    
}
