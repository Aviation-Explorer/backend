package aviation.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Serdeable
public record AirportDto(@MappedProperty("id") String id,
                         @MappedProperty("gmt") String gmt,
                         @MappedProperty("airport_id") String airportId,
                         @BsonProperty("iata_code") String iataCode,
                         @JsonProperty("city_iata_code") String cityIataCode,
                         @MappedProperty("icao_code") String icaoCode,
                         @MappedProperty("country_iso2") String countryIso2,
                         @MappedProperty("geoname_id") String geonameId,
                         @MappedProperty("latitude") String latitude,
                         @MappedProperty("longitude") String longitude,
                         @MappedProperty("airport_name") String airportName,
                         @MappedProperty("phone_number") String phoneNumber,
                         @MappedProperty("country_name") String countryName,
                         @MappedProperty("timezone") String timezone) {
}
