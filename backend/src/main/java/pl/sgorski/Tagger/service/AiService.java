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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
import pl.sgorski.Tagger.exception.AiParsingException;

@Log4j2
@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${ai.system.context}")
    private String systemContext;

    private final OpenAiChatModel chatModel;
    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

    public ItemDescriptionResponse generateResponse(String userPrompt) {
        var outputConverter = new BeanOutputConverter<>(ItemDescriptionResponse.class);
        var jsonSchema = outputConverter.getJsonSchema();
        try {
            var prompt = createPrompt(userPrompt, jsonSchema);
            var content = getAiResponseText(prompt);
            return outputConverter.convert(content);
        } catch (NonTransientAiException e) {
            log.info("Current model does not support JSON schema response format, falling back to text response.");
            var prompt = createPromptWithoutSchemaSupport(userPrompt, jsonSchema);
            var content = getAiResponseText(prompt);
            try {
                return objectMapper.readValue(content, ItemDescriptionResponse.class);
            } catch (Exception ex) {
                log.error("Failed to parse response: {}", content);
                var message = messageSource.getMessage("exception.ai.parsing", null, LocaleContextHolder.getLocale());
                throw new AiParsingException(message);
            }
        } catch (Exception e) {
            var message = messageSource.getMessage("exception.ai.generic", null, LocaleContextHolder.getLocale());
            log.error("Failed to generate response: {}", e.getMessage());
            throw new RuntimeException(message);
        }
    }

    private Prompt createPrompt(String userPrompt, String jsonSchema) {
        return Prompt.builder()
                .messages(new SystemMessage(addLanguageHint(systemContext)), new UserMessage(userPrompt))
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
                """.formatted(systemContext, jsonSchema);
        return Prompt.builder()
                .messages(new SystemMessage(addLanguageHint(schemaHint)), new UserMessage(userPrompt))
                .build();
    }

    private String getAiResponseText(Prompt prompt) {
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

    private String addLanguageHint(String prompt) {
        var language = LocaleContextHolder.getLocale().getLanguage();
        log.info("Adding language hint for language: {}", language);
        return """
                %s
                
                Respond must be in language: %s
                """.formatted(prompt, language);
    }
}
