package aviation.models.flight;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@MappedEntity(value = "flights")
@Getter
@Serdeable
public class Flight {
  @Id @GeneratedValue private final ObjectId _id;
  private final String flightDate;
  private final String flightStatus;
  private final Departure departure;
  private final Arrival arrival;
  private final AirlineFlight airlineFlight;
  private final LiveFlight liveFlight;

  @BsonCreator
  public Flight(
      @BsonProperty("_id") ObjectId _id,
      @BsonProperty("flight_date") String flightDate,
      @BsonProperty("flight_status") String flightStatus,
      @BsonProperty("departure") Departure departure,
      @BsonProperty("arrival") Arrival arrival,
      @BsonProperty("airline") AirlineFlight airlineFlight,
      @BsonProperty("live") LiveFlight liveFlight) {
    this._id = _id;
    this.flightDate = flightDate;
    this.flightStatus = flightStatus;
    this.departure = departure;
    this.arrival = arrival;
    this.airlineFlight = airlineFlight;
    this.liveFlight = liveFlight;
  }
}
