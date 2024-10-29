package authservice.exceptions;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ErrorResponse(Integer statusCode, String message) {}
