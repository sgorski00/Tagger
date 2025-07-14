package pl.sgorski.Tagger.service.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import pl.sgorski.Tagger.model.User;
import pl.sgorski.Tagger.test_config.BaseTestcontainers;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class JwtServiceTests extends BaseTestcontainers {

    @Autowired
    private JwtService jwtService;

    private String jwt;
    private User user;

    @BeforeEach
    void setUp() {
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
}
