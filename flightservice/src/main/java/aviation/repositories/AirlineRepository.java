package aviation.repositories;

import aviation.models.airline.Airline;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

@MongoRepository
public interface AirlineRepository extends CrudRepository<Airline, ObjectId> {}
