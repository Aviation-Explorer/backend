package aviation.repositories;

import aviation.models.flight.Flight;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import org.bson.types.ObjectId;

@MongoRepository
public interface FlightRepository extends CrudRepository<Flight, ObjectId> {
    @MongoFindQuery(filter = "{'departure.iata': :iataCode}}")
    List<Flight> findDeparturesByIataAirport(String iataCode);

    @MongoFindQuery(filter = "{ $and: [ { 'departure.iata': :departureIata }, { 'arrival.iata': :arrivalIata }, { 'flight_date': :flightDate } ] }")
    List<Flight> findFlightsByParameters(@Nullable String departureIata, @Nullable String arrivalIata, @Nullable String flightDate);
}
