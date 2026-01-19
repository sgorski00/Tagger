package pl.sgorski.Tagger.service;

import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;

public interface ItemsService {

    ItemDescriptionResponse getFullInfo(ItemDescriptionRequest request);

    String generatePrompt(ItemDescriptionRequest request);

    default String generateBasePrompt(ItemDescriptionRequest request) {
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
