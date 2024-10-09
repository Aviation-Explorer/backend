package aviation.repository;

import aviation.models.AviationUser;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserRepository extends CrudRepository<AviationUser, Long> {   
    boolean existsByEmail(String email);   
    
    Mono<AviationUser> findByEmail(String email);
    
}
