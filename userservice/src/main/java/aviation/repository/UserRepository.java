package aviation.repository;

import aviation.models.AviationUser;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import reactor.core.publisher.Mono;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserRepository extends CrudRepository<AviationUser, Long> {   
    Mono<Boolean> existsByEmail(String email);

    Mono<AviationUser> findByEmail(String email);
    @Query(value = "DELETE FROM aviation_user WHERE email = :email")
    Mono<Boolean> deleteByEmail(String email);
    
}
