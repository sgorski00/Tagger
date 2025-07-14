package pl.sgorski.Tagger.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomOAuth2UserTest {

    @Test
    void shouldCreateCustomOAuth2User() {
        User user = new User();
        user.setId(1L);
        HashMap<String, Object> attributes = new HashMap<>();

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(user, attributes);

        assertNotNull(customOAuth2User);
        assertEquals(user, customOAuth2User.user());
    }

    @Test
    void shouldReturnUsersAuthorities() {
        User user = new User();
        var auths = user.getAuthorities();
        HashMap<String, Object> attributes = new HashMap<>();
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(user, attributes);

        var result = customOAuth2User.getAuthorities();

        assertNotNull(result);
        assertEquals(auths, result);
    }

    @Test
    void shouldReturnAttributes() {
        User user = new User();
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("key", "value");
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(user, attributes);

        var result = customOAuth2User.getAttributes();

        assertNotNull(result);
        assertEquals(attributes, result);
        assertEquals("value", result.get("key"));
        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnUserName() {
        User user = new User();
        user.setName("test");
        HashMap<String, Object> attributes = new HashMap<>();
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(user, attributes);

        var result = customOAuth2User.getName();

        assertNotNull(result);
        assertEquals("test", result);
    }
}
