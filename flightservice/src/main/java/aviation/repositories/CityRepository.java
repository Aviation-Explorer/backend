package aviation.repositories;

import aviation.models.city.City;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

@MongoRepository
public interface CityRepository extends CrudRepository<City, ObjectId> {}
