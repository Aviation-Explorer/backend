package aviation.backend;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/api")
public class HealthController {
    @Get("/health")
    public String checkHealth() {
        return "Server healthy";
    }
}
