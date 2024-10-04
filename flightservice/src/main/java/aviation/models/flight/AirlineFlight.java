package aviation.models.flight;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Introspected
@Serdeable
@Getter
public class AirlineFlight {
  private final String name;
  private final String iata;
  private final String icao;

  @BsonCreator
  public AirlineFlight(
      @BsonProperty("name") String name,
      @BsonProperty("iata") String iata,
      @BsonProperty("icao") String icao) {
    this.name = name;
    this.iata = iata;
    this.icao = icao;
  }
}
