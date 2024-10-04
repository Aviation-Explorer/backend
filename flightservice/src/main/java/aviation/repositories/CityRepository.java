package aviation.repositories;

import aviation.models.city.City;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import java.util.Optional;
import org.bson.types.ObjectId;

@MongoRepository
public interface CityRepository extends CrudRepository<City, ObjectId> {
    @MongoFindQuery(filter = "{iata_code: :iataCode}}")
    Optional<City> findByIataCode(String iataCode);

}
