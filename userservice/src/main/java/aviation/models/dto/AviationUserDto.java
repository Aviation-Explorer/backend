package aviation.models.dto;

import aviation.utils.Role;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record AviationUserDto(
    String name,
    String surname,
    String email,
    String phoneNumber,
    Integer age,
    Boolean isBlocked,
    Role role) {}
