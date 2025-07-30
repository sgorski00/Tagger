package pl.sgorski.Tagger.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserTests {

    @Test
    void shouldCreateUserFromOAuth2User_unknownName() {
        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(oAuth2User.getAttribute("email")).thenReturn("test@email.com");

        User user = new User(oAuth2User);

        assertNotNull(user);
        assertEquals("test@email.com", user.getEmail());
        assertEquals("Unknown", user.getName());
        assertTrue(user.getPassword().isEmpty());
    }

    @Test
    void shouldCreateUserFromOAuth2User_knownName() {
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("name", "John Doe");

        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(oAuth2User.getAttribute("email")).thenReturn("test@email.com");
        when(oAuth2User.getAttributes()).thenReturn(attributes);

        User user = new User(oAuth2User);

        assertNotNull(user);
        assertEquals("test@email.com", user.getEmail());
        assertEquals("John Doe", user.getName());
        assertTrue(user.getPassword().isEmpty());
    }

    @Test
    void shouldReturnAuthority() {
        User user = new User();

        var result = user.getAuthorities();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ROLE_USER", result.iterator().next().getAuthority());
    }

    @Test
    void shouldReturnEmailAsUsername() {
        User user = new User();
        user.setEmail("test@email.com");

        String result = user.getUsername();

        assertEquals("test@email.com", result);
    }
}
