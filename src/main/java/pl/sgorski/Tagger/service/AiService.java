package pl.sgorski.Tagger.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.PromptResponse;
import pl.sgorski.Tagger.exception.AiParsingException;

@Log4j2
@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${ai.system.context}")
    private String systemContext;

    private final OpenAiChatModel chatModel;

    private final ObjectMapper objectMapper;

    public PromptResponse generateResponse(String userPrompt) {
        var outputConverter = new BeanOutputConverter<>(PromptResponse.class);
        String jsonSchema = outputConverter.getJsonSchema();
        Prompt prompt;
        String content;
        try {
            prompt = createPrompt(userPrompt, jsonSchema);
            content = getAiResponseText(prompt);
            return outputConverter.convert(content);
        } catch (NonTransientAiException e) {
            log.info("Current model does not support JSON schema response format, falling back to text response.");
            prompt = createPromptWithoutSchemaSupport(userPrompt, jsonSchema);
            content = getAiResponseText(prompt);
            try {
                return objectMapper.readValue(content, PromptResponse.class);
            } catch (Exception ex) {
                throw new AiParsingException("Failed to parse AI response: " + ex.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute AI request: " + e.getMessage());
        }
    }

    private Prompt createPrompt(String userPrompt, String jsonSchema) {
        return Prompt.builder()
                .messages(new SystemMessage(systemContext), new UserMessage(userPrompt))
                .chatOptions(OpenAiChatOptions.builder()
                        .responseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                        .build()
                )
                .build();
    }

    private Prompt createPromptWithoutSchemaSupport(String userPrompt, String jsonSchema) {
        String schemaHint = """
                %s
                
                Response should be in JSON format with the following format:
                %s
                """.formatted(
                systemContext,
                jsonSchema
        );
        return Prompt.builder()
                .messages(new SystemMessage(schemaHint), new UserMessage(userPrompt))
                .build();
    }

    private String getAiResponseText(Prompt prompt) {
        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}
