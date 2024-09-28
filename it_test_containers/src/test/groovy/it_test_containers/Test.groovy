package it_test_containers

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

import java.time.Duration

@MicronautTest
@Testcontainers
class Test extends Specification {

    @Inject
    @Client
    HttpClient client

    static MariaDBContainer mariadbContainer = new MariaDBContainer<>(DockerImageName.parse("mariadb:11.2"))
            .withExposedPorts(3306)
            .withUsername("root")
            .withPassword("")
            .withDatabaseName("userDb")
            .withInitScript("sql/seed-data.sql")


    static GenericContainer consulContainer = new GenericContainer("consul:1.15.4")
            .withExposedPorts(8500)


    static GenericContainer userserviceContainer = new GenericContainer("kajtekdocker/userservice:1.0")
            .withExposedPorts(8082)
            .dependsOn(consulContainer, mariadbContainer)
            .withStartupTimeout(Duration.ofMinutes(2))


    def setup() {
        mariadbContainer.start()
        consulContainer.start()
        userserviceContainer.start()
    }


    def "should return user email"() {
        when:
        HttpResponse<?> response = client.toBlocking().exchange("http://${userserviceContainer.getHost()}:${userserviceContainer.getMappedPort(8082)}/api/user/users")

        then:
        response.status() == HttpStatus.UNAUTHORIZED
    }
}