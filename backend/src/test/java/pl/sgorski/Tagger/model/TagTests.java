package pl.sgorski.Tagger.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TagTests {

    @Test
    void shouldCreateTagWithName() {
        String name = "#Tag";

        Tag tag = new Tag(name);

        assertNotNull(tag);
        assertEquals(name, tag.getName());
    }
}
