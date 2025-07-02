package pl.sgorski.Tagger.service;

import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;

public interface ItemsService {

    PromptResponse getFullInfo(PromptRequest request);

    String generatePrompt(PromptRequest request);

    default String generateBasePrompt(PromptRequest request) {
        return """
                Please provide a detailed description of the item, including its features, benefits, and any other relevant information.
                General response info:
                - Item: %s
                - Tags quantity: %s
                - Selling platform: %s
                - Response style: %s
                - Target audience: %s
                """.formatted(
                request.getItem(),
                request.getTagsQuantity(),
                request.getPlatform(),
                request.getResponseStyle(),
                request.getTargetAudience()
        );
    }
}
