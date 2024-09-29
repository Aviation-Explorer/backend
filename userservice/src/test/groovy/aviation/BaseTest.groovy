package aviation

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

abstract class BaseTest extends Specification {
    @Shared
    static MariaDBContainer mariadbContainer = new MariaDBContainer<>(DockerImageName.parse("mariadb:11.2"))
            .withExposedPorts(3306)
            .withUsername("root")
            .withPassword("")
            .withDatabaseName("userDb")
            .waitingFor(Wait.forListeningPort())

    @Shared
    static GenericContainer consulContainer = new GenericContainer("consul:1.15.4")
            .withExposedPorts(8500)
            .waitingFor(Wait.forListeningPort())

    def setupSpec() {
        mariadbContainer.start()
        consulContainer.start()
        print("diupa" + consulContainer.getLogs())
        print("diupa2" + mariadbContainer.getLogs())

    }

    def cleanupSpec() {
        mariadbContainer.stop()
        consulContainer.stop()
    }
}
