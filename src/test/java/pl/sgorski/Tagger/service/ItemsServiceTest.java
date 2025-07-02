package pl.sgorski.Tagger.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemsServiceTest {

    @Mock
    private AiService aiService;

    @InjectMocks
    private ItemsServiceImpl itemsService;

    @Test
    void shouldReturnResponse_getFullInfo() {
        when(aiService.generateResponse(anyString())).thenReturn(new PromptResponse());
        PromptRequest promptRequest = new PromptRequest();

        PromptResponse result = itemsService.getFullInfo(promptRequest);

        assertNotNull(result);
    }

    @Test
    void shouldReturnPrompt_getFullInfo() {
        PromptRequest promptRequest = new PromptRequest();
        promptRequest.setItem("Test Item");
        promptRequest.setTagsQuantity(10);

        String result = itemsService.generatePrompt(promptRequest);

        assertNotNull(result);
        assertTrue(result.contains("Test Item"));
        assertTrue(result.contains("10"));
    }
}
