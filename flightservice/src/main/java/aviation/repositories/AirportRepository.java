package aviation.repositories;

import aviation.models.airport.Airport;
import aviation.models.city.City;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

import java.util.Optional;

@MongoRepository
public interface AirportRepository extends CrudRepository<Airport, ObjectId> {
    @MongoFindQuery(filter = "{iata_code: :iataCode}}")
    Optional<Airport> findByIataCode(String iataCode);
}
