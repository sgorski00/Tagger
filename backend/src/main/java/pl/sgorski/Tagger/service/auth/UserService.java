package pl.sgorski.Tagger.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.ProfileResponse;
import pl.sgorski.Tagger.exception.UserNotFoundException;
import pl.sgorski.Tagger.mapper.UserMapper;
import pl.sgorski.Tagger.model.User;
import pl.sgorski.Tagger.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper profileMapper;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public ProfileResponse getLoggedUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
          .map(profileMapper::toResponse)
          .orElseThrow(() -> new UserNotFoundException("Logged user doesn't exists in the db: " + email));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
