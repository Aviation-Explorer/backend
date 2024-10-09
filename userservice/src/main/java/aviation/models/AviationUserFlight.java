package aviation.models;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Serdeable
@Getter
@Setter
@MappedEntity(value = "aviation_user_flight")
public class AviationUserFlight {
    @Id
    @GeneratedValue
    private Long id;
    private String aviationUserEmail;
    private String departureAirport;
    private String arrivalAirport;
    private Date flightDate;
    private String departureTerminal;
    private String departureGate;
    private String arrivalTerminal;
    private String arrivalGate;
}
