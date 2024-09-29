package aviation.models.airport;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@MappedEntity(value = "airports")
@Getter
@Serdeable
public class Airport {
  @Id @GeneratedValue ObjectId _id;
  String id;
  String gmt;
  String airportId;
  String iataCode;
  String cityIataCode;
  String icaoCode;
  String countryIso2;
  String geonameId;
  String latitude;
  String longitude;
  String airportName;

  @Nullable String phoneNumber;
  String countryName;
  String timezone;

  @BsonCreator
  public Airport(
      @BsonProperty("_id") ObjectId _id,
      @BsonProperty("id") String id,
      @BsonProperty("gmt") String gmt,
      @BsonProperty("airport_id") String airportId,
      @BsonProperty("iata_code") String iataCode,
      @BsonProperty("city_iata_code") String cityIataCode,
      @BsonProperty("icao_code") String icaoCode,
      @BsonProperty("country_iso2") String countryIso2,
      @BsonProperty("geoname_id") String geonameId,
      @BsonProperty("latitude") String latitude,
      @BsonProperty("longitude") String longitude,
      @BsonProperty("airport_name") String airportName,
      @BsonProperty("phone_number") @Nullable String phoneNumber,
      @BsonProperty("country_name") String countryName,
      @BsonProperty("timezone") String timezone) {
    this._id = _id;
    this.id = id;
    this.gmt = gmt;
    this.airportId = airportId;
    this.iataCode = iataCode;
    this.cityIataCode = cityIataCode;
    this.icaoCode = icaoCode;
    this.countryIso2 = countryIso2;
    this.geonameId = geonameId;
    this.latitude = latitude;
    this.longitude = longitude;
    this.airportName = airportName;
    this.phoneNumber = phoneNumber;
    this.countryName = countryName;
    this.timezone = timezone;
  }
}
