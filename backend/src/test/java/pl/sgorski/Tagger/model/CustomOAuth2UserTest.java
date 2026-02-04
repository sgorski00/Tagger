package pl.sgorski.Tagger.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomOAuth2UserTest {

    @Test
    void shouldCreateCustomOAuth2User() {
        var user = new User();
        user.setId(1L);
        var attributes = new HashMap<String, Object>();

        var customOAuth2User = new CustomOAuth2User(user, attributes);

        assertNotNull(customOAuth2User);
        assertEquals(user, customOAuth2User.user());
    }

    @Test
    void shouldReturnUsersAuthorities() {
        var user = new User();
        var auths = user.getAuthorities();
        var attributes = new HashMap<String, Object>();
        var customOAuth2User = new CustomOAuth2User(user, attributes);

        var result = customOAuth2User.getAuthorities();

        assertNotNull(result);
        assertEquals(auths, result);
    }

    @Test
    void shouldReturnAttributes() {
        var user = new User();
        var attributes = new HashMap<String, Object>();
        attributes.put("key", "value");
        var customOAuth2User = new CustomOAuth2User(user, attributes);

        var result = customOAuth2User.getAttributes();

        assertNotNull(result);
        assertEquals(attributes, result);
        assertEquals("value", result.get("key"));
        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnUserName() {
        var user = new User();
        user.setName("test");
        var attributes = new HashMap<String, Object>();
        var customOAuth2User = new CustomOAuth2User(user, attributes);

        var result = customOAuth2User.getName();

        assertNotNull(result);
        assertEquals("test", result);
    }
}
