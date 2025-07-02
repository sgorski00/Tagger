package pl.sgorski.Tagger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;

@Service
@RequiredArgsConstructor
public class ClothesService implements ItemsService {

    private final AiService aiService;

    @Override
    public PromptResponse getFullInfo(PromptRequest request) {
        if (request instanceof ClothesRequest clothesRequest) {
            return aiService.generateResponse(generatePrompt(clothesRequest));
        } else {
            throw new IllegalArgumentException("Invalid request type. Expected ClothesRequest.");
        }
    }

    @Override
    public String generatePrompt(PromptRequest request) {
        if (request instanceof ClothesRequest clothesRequest) {
            return createClothsPrompt(clothesRequest);
        } else {
            throw new IllegalArgumentException("Invalid request type. Expected ClothesRequest.");
        }
    }

    private String createClothsPrompt(ClothesRequest request) {
        return """
                %s
                Describe the clothing item.
                
                Additional info:
                 - Color: %s
                 - Size: %s
                 - Material: %s
                """.formatted(
                generateBasePrompt(request),
                request.getColor(),
                request.getSize(),
                request.getMaterial()
        );
    }
}
