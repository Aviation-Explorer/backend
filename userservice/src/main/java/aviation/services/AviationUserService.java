package aviation.services;

import java.util.List;

import aviation.models.AviationUser;
import aviation.models.RefreshTokenEntity;
import aviation.models.UserCredentials;
import aviation.models.dto.AviationUserDto;
import aviation.repository.RefreshTokenRepository;
import aviation.repository.UserRepository;
import aviation.utils.PasswordManager;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Optional;
import java.util.logging.Logger;

@Singleton
public class AviationUserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final Logger LOGGER = Logger.getLogger("AviationUserService");

    public AviationUserService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public Flux<AviationUserDto> findAll() {
        List<AviationUser> users = userRepository.findAll();        
        return Flux.fromIterable(users)
                .map(this::toDto);
    }

    public Mono<AviationUserDto> save(AviationUser user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            return Mono.error(new IllegalArgumentException("Email already exsits. Must be unique"));
        }
        user.setPassword(PasswordManager.hashPassword(user.getPassword()));
        
        AviationUser userToSave = userRepository.save(user);                

        return Mono.just(toDto(userToSave)); 
    } 
    
    public Mono<Boolean> verifyCredentials(UserCredentials credentials) {
        Optional<AviationUser> optUser = userRepository.findByEmail(credentials.email());

        if(optUser.isPresent()) {
            AviationUser user = optUser.get();

            return Mono.just(PasswordManager.checkPassword(credentials.password(), user.getPassword()));
        }

        return Mono.just(false);

    }

    public Mono<List<RefreshTokenEntity>> getTokens() {
        return Mono.just(refreshTokenRepository.findAll());
    }

    private AviationUserDto toDto(AviationUser user) {
        return new AviationUserDto(user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAge());
    }

}
