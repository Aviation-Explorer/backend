package authservice.clients;

import authservice.models.UserCredentials;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import reactor.core.publisher.Mono;

@Client(id = "userservice")
public interface UserServiceClient {

    @Post("/api/user/verify")
    Mono<Boolean> verifyCredentials(@Body UserCredentials credentials);
    
}
