package aviation.models.flight;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Introspected
@Serdeable
@Getter
public class LiveFlight {
  private final String updated;
  private final Double latitude;
  private final Double longitude;
  private final Double altitude;
  private final Double direction;
  private final Double speedHorizontal;
  private final Double speedVertical;
  private final Boolean isGround;

  @BsonCreator
  public LiveFlight(
      @BsonProperty("updated") String updated,
      @BsonProperty("latitude") Double latitude,
      @BsonProperty("longitude") Double longitude,
      @BsonProperty("altitude") Double altitude,
      @BsonProperty("direction") Double direction,
      @BsonProperty("speed_horizontal") Double speedHorizontal,
      @BsonProperty("speed_vertical") Double speedVertical,
      @BsonProperty("is_ground") Boolean isGround) {
    this.updated = updated;
    this.latitude = latitude;
    this.longitude = longitude;
    this.altitude = altitude;
    this.direction = direction;
    this.speedHorizontal = speedHorizontal;
    this.speedVertical = speedVertical;
    this.isGround = isGround;
  }
}
