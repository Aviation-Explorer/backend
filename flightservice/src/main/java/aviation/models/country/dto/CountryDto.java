package aviation.models.country.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record CountryDto(
    String id,
    String capital,
    String currencyCode,
    String fipsCode,
    String countryIso2,
    String countryIso3,
    String continent,
    String countryId,
    String countryName,
    String currencyName,
    String countryIsoNumeric,
    String phonePrefix,
    String population) {}
