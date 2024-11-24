package aviation.services;

import aviation.models.stats.UserFlightStat;
import aviation.repository.FlightUserRepository;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

@Singleton
public class FlightUserService {
    private final FlightUserRepository flightUserRepository;

    public FlightUserService(FlightUserRepository flightUserRepository) {
        this.flightUserRepository = flightUserRepository;
    }

    public Flux<UserFlightStat> countUsersByAirline() {
        return flightUserRepository.countUsersFlightByAirline();
    }

    


}
