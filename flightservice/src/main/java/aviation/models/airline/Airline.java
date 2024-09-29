package aviation.models.airline;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@MappedEntity(value = "airlines")
@Getter
@Serdeable
public class Airline {
  @Id @GeneratedValue ObjectId _id;

  String id;
  String fleetAverageAge;
  String airlineId;
  String callsign;
  String hubCode;
  String iataCode;
  String icaoCode;
  String countryIso2;
  String dateFounded;
  String iataPrefixAccounting;
  String airlineName;
  String countryName;
  String fleetSize;
  String status;
  String type;

  @BsonCreator
  public Airline(
      @BsonProperty("_id") ObjectId _id,
      @BsonProperty("id") String id,
      @BsonProperty("fleet_average_age") String fleetAverageAge,
      @BsonProperty("airline_id") String airlineId,
      @BsonProperty("callsign") String callsign,
      @BsonProperty("hub_code") String hubCode,
      @BsonProperty("iata_code") String iataCode,
      @BsonProperty("icao_code") String icaoCode,
      @BsonProperty("country_iso2") String countryIso2,
      @BsonProperty("date_founded") String dateFounded,
      @BsonProperty("iata_prefix_accounting") String iataPrefixAccounting,
      @BsonProperty("airline_name") String airlineName,
      @BsonProperty("country_name") String countryName,
      @BsonProperty("fleet_size") String fleetSize,
      @BsonProperty("status") String status,
      @BsonProperty("type") String type) {
    this._id = _id;
    this.id = id;
    this.fleetAverageAge = fleetAverageAge;
    this.airlineId = airlineId;
    this.callsign = callsign;
    this.hubCode = hubCode;
    this.iataCode = iataCode;
    this.icaoCode = icaoCode;
    this.countryIso2 = countryIso2;
    this.dateFounded = dateFounded;
    this.iataPrefixAccounting = iataPrefixAccounting;
    this.airlineName = airlineName;
    this.countryName = countryName;
    this.fleetSize = fleetSize;
    this.status = status;
    this.type = type;
  }
}
