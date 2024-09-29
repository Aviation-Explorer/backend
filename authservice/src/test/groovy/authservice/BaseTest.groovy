package authservice

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

abstract class BaseTest extends Specification {

    static def network = Network.newNetwork()
    @Shared
    static GenericContainer consulContainer = new GenericContainer("consul:1.15.4")
            .withExposedPorts(8500)
            .withNetwork(network)
            .withNetworkMode("host")
            .waitingFor(Wait.forListeningPort())
    @Shared
    static MariaDBContainer mariadbContainer = new MariaDBContainer<>(DockerImageName.parse("mariadb:11.2"))
            .withExposedPorts(3306)
            .withNetwork(network)
            .withNetworkMode("host")
            .withDatabaseName("userDb")
            .withInitScript("sql/init.sql")
            .waitingFor(Wait.forListeningPort())

    @Shared
    static GenericContainer userServiceContainer = new GenericContainer("kajtekdocker/userservice:1.4")
            .withExposedPorts(8082)
            .withNetwork(network)
            .withNetworkMode("host")
            .waitingFor(Wait.forListeningPort())

    def setupSpec() {
        mariadbContainer.start()
        consulContainer.start()
        userServiceContainer.withEnv("DB_HOST", mariadbContainer.getHost())
        userServiceContainer.withEnv("DB_PORT", mariadbContainer.getMappedPort(3306).toString())
        userServiceContainer.withEnv("CONSUL_PORT", consulContainer.getMappedPort(8500).toString())
        userServiceContainer.start()
        print("mariadubpa " + mariadbContainer.getHost() + ":" + mariadbContainer.getMappedPort(3306))
        print("ka3wo" + consulContainer.getHost() + ":" + consulContainer.getMappedPort(8500))
        print("dupa" + userServiceContainer.getLogs())
    }

    def cleanupSpec() {
        mariadbContainer.stop()
        consulContainer.stop()
        userServiceContainer.stop()
    }
}
