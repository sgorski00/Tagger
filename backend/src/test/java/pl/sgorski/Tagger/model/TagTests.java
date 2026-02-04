package pl.sgorski.Tagger.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TagTests {

    @Test
    void shouldCreateTagWithName() {
        var name = "#Tag";

        var tag = new Tag(name);

        assertNotNull(tag);
        assertEquals(name, tag.getName());
    }
}
