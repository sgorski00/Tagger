package pl.sgorski.Tagger.service.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sgorski.Tagger.model.User;
import pl.sgorski.Tagger.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldFindUserByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        var user = userService.findByEmail("test@email.com");

        assertNotNull(user);
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void shouldThrowWhenFindUserByEmail_UserNotExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        var thrown = assertThrows(NoSuchElementException.class, () -> userService.findByEmail("test@email.com"));

        assertTrue(thrown.getMessage().contains("User not found"));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void shouldSaveUser() {
        var user = new User();

        userService.save(user);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldReturnTrueIfUsersExistsByEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        var result = userService.existsByEmail("test@email.com");

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseIfUsersExistsByEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        var result = userService.existsByEmail("test@email.com");

        assertFalse(result);
    }
}
