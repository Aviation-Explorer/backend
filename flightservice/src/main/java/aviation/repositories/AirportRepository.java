package aviation.repositories;

import aviation.models.Airport;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

@MongoRepository(databaseName = "aviationDb")
public interface AirportRepository extends CrudRepository<Airport, ObjectId> {
}
