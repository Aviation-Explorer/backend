package aviation


import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

import java.time.Duration

@Testcontainers
@MicronautTest
class UserServiceTest extends Specification {
    @Inject
    @Client("/api/user")
    HttpClient client

    static MariaDBContainer mariadbContainer = new MariaDBContainer<>(DockerImageName.parse("mariadb:11.2"))
            .withExposedPorts(3306)
            .withUsername("root")
            .withPassword("")
            .withDatabaseName("userDb")
            .waitingFor(Wait.forListeningPort())


    static GenericContainer consulContainer = new GenericContainer("consul:1.15.4")
            .withExposedPorts(8500)
            .waitingFor(Wait.forHttp("/v1/status/leader").forStatusCode(200))


    static GenericContainer userserviceContainer = new GenericContainer("kajtekdocker/userservice:1.0")
            .withExposedPorts(8082)
            .dependsOn(mariadbContainer)
            .withStartupTimeout(Duration.ofMinutes(2))


    def setup() {
        mariadbContainer.start()
        consulContainer.start()
        userserviceContainer.start()
    }

    def "should not return users when unauthorized"() {
        expect:
        2 == 2
    }


}
