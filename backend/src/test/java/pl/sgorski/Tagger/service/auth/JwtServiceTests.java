package pl.sgorski.Tagger.service.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sgorski.Tagger.config.JwtProperties;
import pl.sgorski.Tagger.model.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTests {

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private JwtService jwtService;

    private String jwt;
    private User user;

    @BeforeEach
    void setUp() {
        when(jwtProperties.expirationTime()).thenReturn(3600000L);
        when(jwtProperties.secretKey()).thenReturn("Zm9vYmFyZm9vYmFyZm9vYmFyZm9vYmFyZm9vYmFyZm9vYmFyZm9vYmFyZm9vYmFy");

        user = new User();
        user.setEmail("test@user.com");
        jwt = jwtService.generateToken(user);
    }

    @Test
    void shouldGenerateToken() {
        assertNotNull(jwt);
        assertFalse(jwt.isEmpty());
    }

    @Test
    void shouldReturnCorrectSubjectFromToken() {
        String result = jwtService.extractUsername(jwt);

        assertEquals("test@user.com", result);
    }

    @Test
    void shouldValidateToken() {
        boolean result = jwtService.isTokenValid(jwt, user);

        assertTrue(result);
    }

    @Test
    void shouldNotValidateToken_WrongUser() {
        boolean result = jwtService.isTokenValid(jwt, new User());

        assertFalse(result);
    }

    @Test
    void shouldNotValidateToken_ExpiredToken() {
        when(jwtProperties.expirationTime()).thenReturn(-1L);
        jwt = jwtService.generateToken(user);

        boolean result = jwtService.isTokenValid(jwt, user);

        assertFalse(result);
    }

    @Test
    void shouldReturnClaimEvenIfTokenIsExpired() {
        when(jwtProperties.expirationTime()).thenReturn(-1L);
        jwt = jwtService.generateToken(user);

        String result = jwtService.extractUsername(jwt);

        assertEquals("test@user.com", result);
    }
}
