package aviation.repositories;

import aviation.models.flight.Flight;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

@MongoRepository
public interface FlightRepository extends CrudRepository<Flight, ObjectId> {
}
