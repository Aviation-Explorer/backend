package aviation.models;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
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
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @Nullable    
    private String phoneNumber;
    @Nullable    
    private Integer age;

}
