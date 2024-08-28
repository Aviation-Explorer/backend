package aviation.userService;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/user")
public class UserController {
    @Get("/health")
    public String getStatus() {
        return "User healthy";
    }
}
