package aviation.models.country;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@MappedEntity(value = "countries")
@Getter
@Serdeable
public class Country {
  @Id @GeneratedValue ObjectId _id;
  private final String id;
  private final String capital;
  private final String currencyCode;
  private final String fipsCode;
  private final String countryIso2;
  private final String countryIso3;
  private final String continent;
  private final String countryId;
  private final String countryName;
  private final String currencyName;
  private final String countryIsoNumeric;
  private final String phonePrefix;
  private final String population;

  @BsonCreator
  public Country(
      @BsonProperty("_id") ObjectId _id,
      @BsonProperty("id") String id,
      @BsonProperty("capital") String capital,
      @BsonProperty("currency_code") String currencyCode,
      @BsonProperty("fips_code") String fipsCode,
      @BsonProperty("country_iso2") String countryIso2,
      @BsonProperty("country_iso3") String countryIso3,
      @BsonProperty("continent") String continent,
      @BsonProperty("country_id") String countryId,
      @BsonProperty("country_name") String countryName,
      @BsonProperty("currency_name") String currencyName,
      @BsonProperty("country_iso_numeric") String countryIsoNumeric,
      @BsonProperty("phone_prefix") String phonePrefix,
      @BsonProperty("population") String population) {
    this._id = _id;
    this.id = id;
    this.capital = capital;
    this.currencyCode = currencyCode;
    this.fipsCode = fipsCode;
    this.countryIso2 = countryIso2;
    this.countryIso3 = countryIso3;
    this.continent = continent;
    this.countryId = countryId;
    this.countryName = countryName;
    this.currencyName = currencyName;
    this.countryIsoNumeric = countryIsoNumeric;
    this.phonePrefix = phonePrefix;
    this.population = population;
  }
}
