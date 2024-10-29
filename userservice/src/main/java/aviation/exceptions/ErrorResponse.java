package aviation.exceptions;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ErrorResponse(Integer status, String message) {
}
