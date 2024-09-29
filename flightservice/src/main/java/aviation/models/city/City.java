package aviation.models.city;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@MappedEntity(value = "cities")
@Getter
@Serdeable
public class City {
  @Id @GeneratedValue ObjectId _id;
  String id;
  String gmt;
  String cityId;
  String iataCode;
  String countryIso2;
  String geonameId;
  String latitude;
  String longitude;
  String cityName;
  String timezone;

  @BsonCreator
  public City(
      @BsonProperty("_id") ObjectId _id,
      @BsonProperty("id") String id,
      @BsonProperty("gmt") String gmt,
      @BsonProperty("city_id") String cityId,
      @BsonProperty("iata_code") String iataCode,
      @BsonProperty("country_iso2") String countryIso2,
      @BsonProperty("geoname_id") String geonameId,
      @BsonProperty("latitude") String latitude,
      @BsonProperty("longitude") String longitude,
      @BsonProperty("city_name") String cityName,
      @BsonProperty("timezone") String timezone) {
    this._id = _id;
    this.id = id;
    this.gmt = gmt;
    this.cityId = cityId;
    this.iataCode = iataCode;
    this.countryIso2 = countryIso2;
    this.geonameId = geonameId;
    this.latitude = latitude;
    this.longitude = longitude;
    this.cityName = cityName;
    this.timezone = timezone;
  }
}
