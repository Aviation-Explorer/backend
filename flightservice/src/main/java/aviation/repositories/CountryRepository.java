package aviation.repositories;

import aviation.models.country.Country;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import org.bson.types.ObjectId;

@MongoRepository
public interface CountryRepository extends CrudRepository<Country, ObjectId> {}
