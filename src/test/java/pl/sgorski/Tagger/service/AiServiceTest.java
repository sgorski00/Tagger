package pl.sgorski.Tagger.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.context.MessageSource;
import org.springframework.web.client.RestClientException;
import pl.sgorski.Tagger.dto.PromptResponse;
import pl.sgorski.Tagger.exception.AiParsingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AiServiceTest {

    @Mock
    private OpenAiChatModel model;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private AiService aiService;

    @Mock
    ChatResponse chatResponse;
    @Mock
    Generation generation;
    @Mock
    AssistantMessage assistantMessage;

    @BeforeEach
    void setUp() throws Exception {
        var field = AiService.class.getDeclaredField("systemContext");
        field.setAccessible(true);
        field.set(aiService, "Test context");
    }

    @Test
    void shouldReturn_GenerateResponseWithJsonSchema() {
        when(model.call(any(Prompt.class))).thenReturn(chatResponse);
        when(chatResponse.getResult()).thenReturn(generation);
        when(generation.getOutput()).thenReturn(assistantMessage);
        when(assistantMessage.getText()).thenReturn("{\"title\":\"T-shirt\",\"description\":\"cotton tshirt\",\"tags\":[\"#fashion\",\"#cotton\"]}");

        String userPrompt = "Describe the clothing item.";

        PromptResponse result = aiService.generateResponse(userPrompt);

        assertNotNull(result);
        assertEquals("T-shirt", result.getTitle());
        assertFalse(result.getDescription().isBlank());
        assertEquals(2, result.getTags().length);
    }

    @Test
    void shouldReturn_GenerateResponseWithObjectMapper() throws Exception {
        PromptResponse promptResponse = new PromptResponse();
        promptResponse.setTitle("T-shirt");
        promptResponse.setDescription("Cotton tshirt");
        promptResponse.setTags(new String[]{"#fashion", "#cotton"});

        when(model.call(any(Prompt.class)))
                .thenThrow(new NonTransientAiException("Model does not support JSON schema"))
                .thenReturn(chatResponse);
        when(chatResponse.getResult()).thenReturn(generation);
        when(generation.getOutput()).thenReturn(assistantMessage);
        when(assistantMessage.getText()).thenReturn("here should be a JSON response");
        when(objectMapper.readValue(anyString(), any(Class.class))).thenReturn(promptResponse);

        String userPrompt = "Describe the clothing item.";

        PromptResponse result = aiService.generateResponse(userPrompt);

        assertNotNull(result);
        assertEquals("T-shirt", result.getTitle());
        assertFalse(result.getDescription().isBlank());
        assertEquals(2, result.getTags().length);
    }

    @Test
    void shouldThrow_GenerateResponseWithObjectMapper_WrongAiResponseFormat() throws Exception {
        when(model.call(any(Prompt.class)))
                .thenThrow(new NonTransientAiException("Model does not support JSON schema"))
                .thenReturn(chatResponse);
        when(chatResponse.getResult()).thenReturn(generation);
        when(generation.getOutput()).thenReturn(assistantMessage);
        when(assistantMessage.getText()).thenReturn("here should be a JSON response");
        when(objectMapper.readValue(anyString(), any(Class.class))).thenThrow(JsonMappingException.class);

        String userPrompt = "Describe the clothing item.";

        assertThrows(AiParsingException.class, () -> aiService.generateResponse(userPrompt));
    }

    @Test
    void shouldThrow_GenerateResponseWithJsonSchema_ServerError() throws Exception {
        when(model.call(any(Prompt.class))).thenThrow(new RestClientException("Server error"));

        String userPrompt = "Describe the clothing item.";

        assertThrows(RuntimeException.class, () -> aiService.generateResponse(userPrompt));
    }
}
