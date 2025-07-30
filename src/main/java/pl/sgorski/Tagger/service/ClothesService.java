package pl.sgorski.Tagger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;

@Service
@RequiredArgsConstructor
public class ClothesService implements ItemsService {

    private final AiService aiService;
    private final MessageSource messageSource;

    @Override
    public ItemDescriptionResponse getFullInfo(ItemDescriptionRequest request) {
        if (request instanceof ClothesRequest clothesRequest) {
            return aiService.generateResponse(generatePrompt(clothesRequest));
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("exception.illegal.argument",new String[]{"Clothes request"}, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public String generatePrompt(ItemDescriptionRequest request) {
        if (request instanceof ClothesRequest clothesRequest) {
            return createClothsPrompt(clothesRequest);
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("exception.illegal.argument",new String[]{"Clothes request"}, LocaleContextHolder.getLocale()));
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
