package aviation.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/user")
public class HealthController {
    @Get("/health")
    public String getStatus() {
        return "User service healthy";
    }
}
