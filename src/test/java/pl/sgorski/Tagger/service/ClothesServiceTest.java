package pl.sgorski.Tagger.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClothesServiceTest {

    @Mock
    private AiService aiService;

    @InjectMocks
    private ClothesService clothesService;

    @Test
    void shouldReturnResponse_getFullInfo() {
        ClothesRequest request = new ClothesRequest();
        when(aiService.generateResponse(any(String.class))).thenReturn(new PromptResponse());

        PromptResponse result = clothesService.getFullInfo(request);

        assertNotNull(result);
    }

    @Test
    void shouldThrow_getFullInfo_WrongRequest() {
        PromptRequest request = new PromptRequest();

        assertThrows(IllegalArgumentException.class, () -> clothesService.getFullInfo(request));
    }

    @Test
    void shouldReturnPrompt_generatePrompt() {
        ClothesRequest request = new ClothesRequest();
        request.setPlatform("Vinted");
        request.setColor("Red");
        request.setMaterial("100% cotton");

        String result = clothesService.generatePrompt(request);

        assertFalse(result.isBlank());
        assertTrue(result.contains("clothing item"));
        assertTrue(result.contains("Red"));
        assertTrue(result.contains("100% cotton"));
        assertTrue(result.contains("Vinted"));
    }

    @Test
    void shouldThrow_generatePrompt_WrongRequest() {
        PromptRequest request = new PromptRequest();

        assertThrows(IllegalArgumentException.class, () -> clothesService.generatePrompt(request));
    }
}
