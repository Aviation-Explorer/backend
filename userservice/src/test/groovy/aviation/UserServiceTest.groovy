package aviation

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Unroll

@Testcontainers
@MicronautTest
class UserServiceTest extends BaseTest {
    @Inject
    @Client("/api/user")
    @Shared
    HttpClient client

    @Inject
    JwtTokenGenerator jwtGenerator

    String generateJWTToken(Map<String, String> claims) {
        return jwtGenerator.generateToken(claims).get()
    }

    @Unroll
    def "should return unauthorized for endpoints #endpoint when no or invalid JWT token"() {
        when: "Sending a GET request to the endpoint"
        client.toBlocking().exchange(HttpRequest.GET(endpoint).bearerAuth(token))

        then: "An HttpClientResponseException is thrown"
        HttpClientResponseException exception = thrown(HttpClientResponseException)
        exception.status == expectedStatus

        where:
        endpoint | token                        | expectedStatus
        "/users" | null                         | HttpStatus.UNAUTHORIZED
        "/users" | "invalid-token"              | HttpStatus.UNAUTHORIZED
        "/users" | null                         | HttpStatus.UNAUTHORIZED
    }

    @Unroll
    def "should return status ok when authorized"() {
        when:
        def response = client.toBlocking().exchange(HttpRequest.GET(endpoint).bearerAuth(token))

        then:
        response.status == expectedStatus

        where:
        endpoint | token                                  | expectedStatus
        "/users" | generateJWTToken(['sub': 'basicUser']) | HttpStatus.OK

    }


}
