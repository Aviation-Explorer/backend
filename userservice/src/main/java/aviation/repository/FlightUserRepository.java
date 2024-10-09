package aviation.repository;

import aviation.models.AviationUserFlight;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import reactor.core.publisher.Flux;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface FlightUserRepository extends CrudRepository<AviationUserFlight, Long> {
  @Query(value = "SELECT * FROM aviation_user_flight WHERE aviation_user_email = :email")
  Flux<AviationUserFlight> findByUserEmail(String email);
}
