package aviation.models;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Serdeable
@MappedEntity(value = "aviation_user")
@NoArgsConstructor
@Getter
@Setter
public class AviationUser {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$",
            message = "Password must be 8-20 characters long, contain at least one digit, one lowercase, one uppercase letter, and one special character."
    )
    private String password;
    @NotNull
    private String email;
    @Nullable    
    private String phoneNumber;
    @Nullable    
    private Integer age;

}
