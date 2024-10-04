package aviation.models.flight;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Introspected
@Serdeable
@Getter
public class Arrival {
  private final String airport;
  private final String timezone;
  private final String iata;
  private final String icao;
  private final String terminal;
  private final String gate;
  private final String baggage;
  private final Integer delay;
  private final String scheduled;
  private final String estimated;
  private final String actual;
  private final String estimatedRunway;
  private final String actualRunway;

  @BsonCreator
  public Arrival(
      @BsonProperty("airport") String airport,
      @BsonProperty("timezone") String timezone,
      @BsonProperty("iata") String iata,
      @BsonProperty("icao") String icao,
      @BsonProperty("terminal") String terminal,
      @BsonProperty("gate") String gate,
      @BsonProperty("baggage") String baggage,
      @BsonProperty("delay") Integer delay,
      @BsonProperty("scheduled") String scheduled,
      @BsonProperty("estimated") String estimated,
      @BsonProperty("actual") String actual,
      @BsonProperty("estimated_runway") String estimatedRunway,
      @BsonProperty("actual_runway") String actualRunway) {
    this.airport = airport;
    this.timezone = timezone;
    this.iata = iata;
    this.icao = icao;
    this.terminal = terminal;
    this.gate = gate;
    this.baggage = baggage;
    this.delay = delay;
    this.scheduled = scheduled;
    this.estimated = estimated;
    this.actual = actual;
    this.estimatedRunway = estimatedRunway;
    this.actualRunway = actualRunway;
  }
}
