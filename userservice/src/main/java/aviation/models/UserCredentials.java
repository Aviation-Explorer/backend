package aviation.models;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Serdeable
public record UserCredentials(
    String email,
    @NotNull
        @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$",
            message =
                "Password must be 8-20 characters long, contain at least one digit, one lowercase, one uppercase letter, and one special character.")
        String password) {}
