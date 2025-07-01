package pl.sgorski.Tagger.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.PromptResponse;
import pl.sgorski.Tagger.exception.AiParsingException;

@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${ai.system.context}")
    private String systemContext;

    private static final String FORMAT_HINT = """
        Please provide a pure JSON response with the following structure:
        {
          "title": "...",
          "description": "...",
          "tags": ["#...", "#..."]
        }
        """;

    private final OpenAiChatModel chatModel;

    private final ObjectMapper objectMapper;

    public PromptResponse generateResponse(String userPrompt) {
        String formattedPrompt = userPrompt + "\n" + FORMAT_HINT;
        Prompt prompt = createPrompt(formattedPrompt);
        String content = getAiResponseText(prompt);
        try {
            return objectMapper.readValue(content, PromptResponse.class);
        } catch (Exception e) {
            throw new AiParsingException("Failed to parse AI response: " + e.getMessage());
        }
    }

    private Prompt createPrompt(String userPrompt) {
        return Prompt.builder()
                .messages(new SystemMessage(systemContext), new UserMessage(userPrompt))
                .build();
    }

    private String getAiResponseText(Prompt prompt) {
        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}
