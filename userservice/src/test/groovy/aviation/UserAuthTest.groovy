package aviation

import aviation.exceptions.DuplicateEmailException
import aviation.exceptions.InvalidPasswordException
import aviation.models.AviationUser
import aviation.repository.UserRepository
import aviation.services.AviationUserService
import aviation.utils.PasswordManager
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.testcontainers.containers.MariaDBContainer
import spock.lang.Specification
import spock.lang.Unroll

@MicronautTest
class UserAuthTest extends Specification {

    @Inject
    AviationUserService userService

    @Inject
    UserRepository userRepository

    static MariaDBContainer mariadbContainer = new MariaDBContainer<>("mariadb:latest")
            .withExposedPorts(3306)
            .withUsername("root")
            .withPassword("")
            .withDatabaseName("userDb")

    void setup() {
        mariadbContainer.start()

        def name = "Ryan"
        def surname = "Gosling"
        def email = "ryangosling@example.com"
        def rawPassword = "password123@DDD"
        def initialUser = new AviationUser(name: name, surname: surname, email: email, password: rawPassword)

        userService.save(initialUser).block()
    }

    def "should hash password before saving user"() {
        given:
        def name = "Test"
        def surname = "Test"
        def email = "test@example.com"
        def rawPassword = "vAliDp4%word"

        def user = new AviationUser(name: name, surname: surname, email: email, password: rawPassword)

        when:
        userService.save(user).block()

        then:
        def savedUser = userRepository.findByEmail(email).block()
        savedUser != null
        PasswordManager.checkPassword(rawPassword, savedUser.password)
    }

    @Unroll
    def "should throw InvalidPasswordException for invalid password: #password"() {
        given:
        def name = "Test"
        def surname = "Test"
        def email = "test@example.com"
        def user = new AviationUser(name: name, surname: surname, email: email, password: password)

        when:
        userService.save(user).block()

        then:
        def exception = thrown(InvalidPasswordException)
        exception.message == "Password violations"

        where:
        password << [
                "",
                "short",
                "noSpecialChar123",
                "NoNumber@",
                "n0uppercasechar!",
                "noSpecial123",
                "12345678"
        ]
    }

    @Unroll
    def "should throw duplicate email exception when trying to save user with email: #email"() {
        given:
        def name1 = "Test"
        def surname1 = "Test"
        def rawPassword1 = "password123@gDDD"

        def user1 = new AviationUser(name: name1, surname: surname1, email: email, password: rawPassword1)

        when:
        userService.save(user1).block()

        then:
        def duplicateException = thrown(DuplicateEmailException)
        duplicateException.message == "Duplicate email violation"

        where:
        email = "ryangosling@example.com"

    }
}
