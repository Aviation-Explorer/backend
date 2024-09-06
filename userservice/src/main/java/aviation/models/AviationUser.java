package aviation.models;

import java.util.UUID;

import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Serdeable
@MappedEntity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AviationUser {
    @Id    
    @GeneratedValue(GeneratedValue.Type.UUID)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    private String phoneNumber;
    private Integer age;    

}
