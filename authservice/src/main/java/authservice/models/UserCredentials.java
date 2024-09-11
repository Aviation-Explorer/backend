package authservice.models;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UserCredentials(String email, String password) {    
}
