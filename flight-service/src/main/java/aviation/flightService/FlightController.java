package aviation.flightService;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/flight")
public class FlightController {
    @Get("/health")
    public String getStatus() {
        return "Flight healthy";
    }
}
