package aviation.models.city.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record CityDto(
    String id,
    String gmt,
    String cityId,
    String iataCode,
    String countryIso2,
    String geonameId,
    String latitude,
    String longitude,
    String cityName,
    String timezone) {}
