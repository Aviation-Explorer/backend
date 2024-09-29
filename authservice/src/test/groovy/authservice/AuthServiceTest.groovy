package authservice

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Unroll

@MicronautTest
@Testcontainers
class AuthServiceTest extends BaseTest {

    @Inject
    @Client("/")
    @Shared
    HttpClient client

    @Unroll
    def "should return token if user log in successful"() {
        given:
        def credentials = new UsernamePasswordCredentials(email, password)

        when:
        def request = HttpRequest.POST("/login", credentials)
        def response = client.toBlocking().exchange(request, BearerAccessRefreshToken.class)

        then:
        response.status == HttpStatus.OK
        response.body() != null
        response.body().accessToken != null

        where:
        email            | password
        "user@email.com" | "password"
    }

    def "should return 401 Unauthorized if user login fails"() {
        given:
        def credentials = new UsernamePasswordCredentials(email, password)

        when:
        def request = HttpRequest.POST("/login", credentials)
        def response = client.toBlocking().exchange(request, BearerAccessRefreshToken.class)

        then:
        response.status == HttpStatus.OK
        response.body() != null
        response.body().accessToken != null

        where:
        email            | password
        "bad@email.com" | "bad_password"
    }
}
