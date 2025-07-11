package pl.sgorski.Tagger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;

@Service
@RequiredArgsConstructor
public class ElectronicsService implements ItemsService {

    private final AiService aiService;
    private final MessageSource messageSource;

    @Override
    public PromptResponse getFullInfo(PromptRequest request) {
        if (request instanceof ElectronicsRequest electronicsRequest) {
            return aiService.generateResponse(generatePrompt(electronicsRequest));
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("exception.illegal.argument",new String[]{"Electronics request"}, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public String generatePrompt(PromptRequest request) {
        if (request instanceof ElectronicsRequest electronicsRequest) {
            return createElectronicsPrompt(electronicsRequest);
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("exception.illegal.argument",new String[]{"Electronics request"}, LocaleContextHolder.getLocale()));
        }
    }

    private String createElectronicsPrompt(ElectronicsRequest request) {
        return """
                %s
                Describe the electronic item.
                
                Additional info:
                - Brand: %s
                - Model: %s
                - Specifications: %s
                - Color: %s
                - Months of Warranty: %s
                """.formatted(
                generateBasePrompt(request),
                request.getBrand(),
                request.getModel(),
                request.getSpecifications(),
                request.getColor(),
                request.getMonthsOfWarranty()
        );
    }
}
