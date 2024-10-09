package aviation

import org.testcontainers.consul.ConsulContainer
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

abstract class BaseTest extends Specification {

    static MariaDBContainer mariadbContainer = new MariaDBContainer<>(DockerImageName.parse("mariadb:11.2"))
            .withExposedPorts(3306)
            .withUsername("root")
            .withPassword("")
            .withDatabaseName("userDb")

    static ConsulContainer consulContainer = new ConsulContainer(DockerImageName.parse("consul:1.15.4"))

    def setupSpec() {
//        mariadbContainer.start()
//        consulContainer.start()

    }

}
