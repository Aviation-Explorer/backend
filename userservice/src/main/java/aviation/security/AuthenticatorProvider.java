package aviation.security;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.AuthenticationProvider;
import io.micronaut.security.token.jwt.validator.JsonWebTokenValidator;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AuthenticatorProvider implements AuthenticationProvider {

    @Inject
    JsonWebTokenValidator validator;

    @Override
    public @NonNull AuthenticationResponse authenticate(@Nullable Object requestContext,
            @NonNull AuthenticationRequest authRequest) {
                String token = authRequest.getIdentity().toString();
                try {
                    var validationResponse = validator.validate(token, authRequest);
                    if(validationResponse.isPresent()) {
                        return AuthenticationResponse.success(token);
                    }
                } catch(Exception e) {
                    return AuthenticationResponse.failure(e.getMessage());
                } 
                return AuthenticationResponse.failure("Invalid auth request");      
    }

}
