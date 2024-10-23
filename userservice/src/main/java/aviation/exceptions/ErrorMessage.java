package aviation.exceptions;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ErrorMessage(Integer status, String message) {
}
