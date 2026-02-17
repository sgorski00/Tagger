package pl.sgorski.Tagger.service.auth;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.sgorski.Tagger.dto.ProfileResponse;
import pl.sgorski.Tagger.mapper.UserMapper;
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

    @Mock
    private UserMapper profileMapper;

    @InjectMocks
    private UserService userService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

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

    @Test
    void shouldReturnLoggedUser() {
        setAuthContext();
        var profileResponse = new ProfileResponse("Test User", "test@email.com", null);

        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(new User()));
        when(profileMapper.toResponse(any(User.class))).thenReturn(profileResponse);

        var result = userService.getLoggedUser();

        assertNotNull(result);
        assertEquals("test@email.com", result.email());
        verify(userRepository, times(1)).findByEmail("test@email.com");
        verify(profileMapper, times(1)).toResponse(any(User.class));
    }

    @Test
    void shouldThrowWhenGetLoggedUser_UserNotExists() {
        setAuthContext();

        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.empty());

        var thrown = assertThrows(NoSuchElementException.class, () -> userService.getLoggedUser());

        assertFalse(thrown.getMessage().isBlank());
        verify(userRepository, times(1)).findByEmail("test@email.com");
    }

    private void setAuthContext() {
        var auth = new UsernamePasswordAuthenticationToken("test@email.com", null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
