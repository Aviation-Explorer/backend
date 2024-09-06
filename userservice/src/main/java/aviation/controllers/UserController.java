package aviation.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import aviation.models.AviationUser;
import aviation.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.validation.constraints.NotBlank;

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/api/userservice")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Get("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "User Service is up and running!";
    }

    @Get("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AviationUser> getUsers() {
        return userRepository.findAll();
    }

    @Post
    public HttpResponse<AviationUser> saveUser(@Body("user") @NotBlank AviationUser user) {
        AviationUser userToSave = userRepository.save(user);

        return HttpResponse
        .created(userToSave)
        .headers(headers -> headers.location(location(userToSave.getId())));
    }

    protected URI location(UUID id) {
        return URI.create("/user/" + id);
    }


}
