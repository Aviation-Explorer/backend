package aviation.services;

import aviation.exceptions.DuplicateEmailException;
import aviation.exceptions.ExistRelationException;
import aviation.exceptions.InvalidPasswordException;
import aviation.exceptions.NotFoundException;
import aviation.models.AviationUser;
import aviation.models.AviationUserFlight;
import aviation.models.UserCredentials;
import aviation.models.dto.AviationUserDto;
import aviation.models.dto.AviationUserFlightDto;
import aviation.models.stats.UserCreatedStat;
import aviation.models.stats.UserFlightStat;
import aviation.repository.FlightUserRepository;
import aviation.repository.UserRepository;
import aviation.utils.PasswordManager;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
@Slf4j
public class AviationUserService {
  private final UserRepository userRepository;
  private final FlightUserRepository flightUserRepository;
  private final Validator validator;

  public AviationUserService(
      UserRepository userRepository,
      FlightUserRepository flightUserRepository,
      Validator validator) {
    this.userRepository = userRepository;
    this.flightUserRepository = flightUserRepository;
    this.validator = validator;
  }

  public Flux<AviationUserDto> findAll() {
    List<AviationUser> users = userRepository.findAll();
    return Flux.fromIterable(users).map(this::toDto);
  }

  public Mono<AviationUserDto> findOne(String email) {
    return userRepository.findByEmail(email).map(this::toDto);
  }

  public Mono<AviationUserDto> save(AviationUser user) {
    Set<ConstraintViolation<AviationUser>> violations = validator.validate(user);

    if (!violations.isEmpty()) {
      return Mono.error(new InvalidPasswordException("Password violations"));
    }

    if (Boolean.TRUE.equals(userRepository.existsByEmail(user.getEmail()).block())) {
      return Mono.error(new DuplicateEmailException("Duplicate email violation"));
    }

    user.setPassword(PasswordManager.hashPassword(user.getPassword()));
    user.setIsBlocked(false);

    AviationUser userToSave = userRepository.save(user);

    return Mono.just(toDto(userToSave));
  }

  public Mono<Boolean> verifyCredentials(UserCredentials credentials) {
    return userRepository
        .findByEmail(credentials.email())
        .flatMap(
            user -> {
              boolean isPasswordValid =
                  PasswordManager.checkPassword(credentials.password(), user.getPassword());
              return Mono.just(isPasswordValid);
            })
        .defaultIfEmpty(false);
  }

  public Mono<Boolean> updatePassword(UserCredentials credentials) {
    Set<ConstraintViolation<UserCredentials>> violations = validator.validate(credentials);

    if (!violations.isEmpty()) {
      return Mono.error(new InvalidPasswordException("Password violations"));
    }

    return userRepository
        .findByEmail(credentials.email())
        .flatMap(
            user -> {
              user.setPassword(PasswordManager.hashPassword(credentials.password()));
              userRepository.update(user);
              return Mono.just(true);
            })
        .switchIfEmpty(
            Mono.error(new NotFoundException("User not found with email: " + credentials.email())));
  }

  public Mono<AviationUserFlight> saveFlightForUser(
      String email, AviationUserFlight aviationUserFlight) {

    Boolean assignedYet = flightUserRepository.ifExists(email, aviationUserFlight.getFlightId());

    if (assignedYet) {
      return Mono.error(new ExistRelationException("User already assigned to this flight"));
    }

    return userRepository
        .findByEmail(email)
        .map(
            user -> {
              aviationUserFlight.setAviationUserEmail(user.getEmail());
              return flightUserRepository.save(aviationUserFlight);
            })
        .switchIfEmpty(Mono.error(new NotFoundException("User not found with email: " + email)));
  }

  public Mono<Boolean> updateUserStatus(String email, Boolean isBlocked) {
    return userRepository
        .findByEmail(email)
        .flatMap(
            user -> {
              user.setIsBlocked(isBlocked);
              userRepository.update(user);
              return Mono.just(true);
            })
        .switchIfEmpty(Mono.error(new NotFoundException("User not found with email: " + email)));
  }

  public Flux<AviationUserFlightDto> getFlightsForUser(String email) {
      Flux<AviationUserFlightDto> map = flightUserRepository.findByUserEmail(email).map(this::toFlightDto);
      AviationUserFlightDto aviationUserFlightDto = map.blockFirst();
      System.out.println(aviationUserFlightDto);

      return map;
  }

  public Mono<Long> countBlockedUsers() {
    return userRepository.countBlockedUsers();
  }

  public Flux<UserCreatedStat> countCreatedAtUsers() {
        return userRepository.countUserCreated();
    }

  public Mono<Void> deleteFlightForUser(String email, Long id) {
    return flightUserRepository
        .deleteFlightByEmailAndID(email, id)
        .flatMap(
            rowsAffected -> {
              if (rowsAffected > 0) {
                return Mono.empty();
              } else {
                return Mono.error(
                    new IllegalArgumentException("No flight found to delete for user: " + email));
              }
            });
  }

    public Mono<Long> deleteUser(String email) {
        return userRepository.findByEmail(email)
                .flatMap(user -> userRepository.deleteByEmail(email))
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with email: " + email)));
    }

  private AviationUserDto toDto(AviationUser user) {
    return new AviationUserDto(
        user.getName(),
        user.getSurname(),
        user.getEmail(),
        user.getPhoneNumber(),
        user.getAge(),
        user.getIsBlocked(),
        user.getRole());
  }

  private AviationUserFlightDto toFlightDto(AviationUserFlight flight) {
    return new AviationUserFlightDto(
        flight.getId(),
        flight.getFlightId(),
        flight.getAirline(),
        flight.getAviationUserEmail(),
        flight.getDepartureAirport(),
        flight.getArrivalAirport(),
        flight.getFlightDate(),
        flight.getDepartureTerminal(),
        flight.getDepartureGate(),
        flight.getArrivalTerminal(),
        flight.getArrivalGate());
  }
}
