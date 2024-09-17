package user_service_test

import aviation.controllers.UserController
import aviation.models.dto.AviationUserDto
import io.micronaut.core.io.ResourceLoader
import io.micronaut.test.annotation.Sql
import jakarta.inject.Inject
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.MountableFile
import spock.lang.Specification

import java.sql.Connection

@Testcontainers
@Sql(scripts = "classpath:sql/init-data.sql", phase = Sql.Phase.BEFORE_EACH)
class It_test_containersSpec extends Specification {

    static MariaDBContainer mariaDBContainer = new MariaDBContainer<>("mariadb:11.2")
    .withCopyFileToContainer(MountableFile.forClasspathResource("sql/init-data.sql"), "/docker-entrypoint-initdb.d/init-data.sql")

    @Inject
    Connection connection
    @Inject
    ResourceLoader resourceLoader
    @Inject
    UserController controller

    def 'should get all users'(){
        given:
        List<AviationUserDto> users

        when:
        users = controller.getUsers()

        then:
        users.size() == 1

    }

}
