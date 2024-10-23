package aviation.models.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record AviationUserDto(
        String name,
        String surname,
        String email,
        String phoneNumber,
        Integer age) {
}
