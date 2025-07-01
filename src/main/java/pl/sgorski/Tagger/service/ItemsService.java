package pl.sgorski.Tagger.service;

import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;

public interface ItemsService {

    PromptResponse getFullInfo(PromptRequest request);

    String generatePrompt(PromptRequest request);

    default String generateBasePrompt(PromptRequest request) {
        return String.format(
                "Item: %s. " +
                        "Create %s tags. " +
                        "Platform: %s. " +
                        "Style: %s. " +
                        "Target audience: %s. ",
                request.getItem(),
                request.getTagsQuantity(),
                request.getPlatform(),
                request.getResponseStyle(),
                request.getTargetAudience()
        );
    }
}
