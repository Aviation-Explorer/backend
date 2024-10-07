package aviation

import io.micronaut.context.annotation.Property
import io.micronaut.test.support.TestPropertyProvider
import org.testcontainers.consul.ConsulContainer
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

@Property(name = "datasources.default.url", value = "jdbc:tc:mariadb:11.2:///userDb")
abstract class BaseTest extends Specification implements TestPropertyProvider {

    static MariaDBContainer mariadbContainer = new MariaDBContainer<>(DockerImageName.parse("mariadb:11.2"))
            .withExposedPorts(3306)
            .withUsername("root")
            .withPassword("")
            .withDatabaseName("userDb")

    static ConsulContainer consulContainer = new ConsulContainer(DockerImageName.parse("consul:1.15.4"))
            .withExposedPorts(8500)

    def setupSpec() {
        mariadbContainer.start()
        consulContainer.start()

    }

    @Override
    Map<String, String> getProperties() {
        return [
                "CONSUL_HOST"              : consulContainer.getHost(),
                "CONSUL_PORT"              : consulContainer.getMappedPort(8500).toString(),
                "consul.client.defaultZone": "http://${consulContainer.getHost()}:${consulContainer.getMappedPort(8500)}"
        ]
    }
}
