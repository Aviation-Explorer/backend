package aviation;

import aviation.models.AviationUser;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

@Client("/api/user")
public interface UserClient {
    @Get("/users")
    List<AviationUser> getUsers();
}
