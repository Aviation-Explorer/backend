package authservice

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

import java.time.Duration

abstract class BaseTest extends Specification {

    static Network network = Network.newNetwork()

    @Shared
    static GenericContainer consulContainer = new GenericContainer("consul:1.15.4")
            .withNetwork(network)
            .withExposedPorts(8500)

    @Shared
    static MariaDBContainer mariadbContainer = new MariaDBContainer<>(DockerImageName.parse("mariadb:11.2"))
            .withNetwork(network)
            .withNetworkAliases("mariadb")
            .withExposedPorts(3306)
            .withDatabaseName("userDb")
            .withInitScript("sql/init.sql")
            .waitingFor(Wait.forListeningPort())

    @Shared
    static GenericContainer userServiceContainer = new GenericContainer("kajtekdocker/userservice:1.5")
            .withNetwork(network)
            .withExposedPorts(8082)
            .waitingFor(Wait.forListeningPort()).withStartupTimeout(Duration.ofSeconds(50))

    def setupSpec() {
        mariadbContainer.start()
        consulContainer.start()
        userServiceContainer.withEnv("DB_HOST", "localhost")
        userServiceContainer.withEnv("DB_PORT", mariadbContainer.getMappedPort(3306).toString())
        userServiceContainer.withEnv("CONSUL_HOST", "consul")
        userServiceContainer.withEnv("CONSUL_PORT", consulContainer.getMappedPort(8500).toString())
        userServiceContainer.start()
    }
}
