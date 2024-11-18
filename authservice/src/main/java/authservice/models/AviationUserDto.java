package authservice.models;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record AviationUserDto(
        String name,
        String surname,
        String email,
        String phoneNumber,
        Integer age,
        Boolean isBlocked,
        String role) {
}
