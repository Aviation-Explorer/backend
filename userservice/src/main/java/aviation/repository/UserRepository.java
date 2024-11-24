package aviation.repository;

import aviation.models.AviationUser;
import aviation.models.stats.UserCreatedStat;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserRepository extends CrudRepository<AviationUser, Long> {
    Mono<Boolean> existsByEmail(String email);

    Mono<AviationUser> findByEmail(String email);

    @Query(value = "DELETE FROM aviation_user WHERE email = :email")
    Mono<Long> deleteByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM aviation_user WHERE is_blocked = true")
    Mono<Long> countBlockedUsers();

    @Query(value = "SELECT DATE_FORMAT(created_at, '%Y-%m-%d') AS createdAt, COUNT(*) AS count FROM aviation_user GROUP BY DATE_FORMAT(created_at, '%Y-%m-%d')")
    Flux<UserCreatedStat> countUserCreated();

}
