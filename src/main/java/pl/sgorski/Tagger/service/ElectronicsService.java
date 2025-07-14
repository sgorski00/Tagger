package pl.sgorski.Tagger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;

@Service
@RequiredArgsConstructor
public class ElectronicsService implements ItemsService {

    private final AiService aiService;

    @Override
    public ItemDescriptionResponse getFullInfo(ItemDescriptionRequest request) {
        if (request instanceof ElectronicsRequest electronicsRequest) {
            return aiService.generateResponse(generatePrompt(electronicsRequest));
        } else {
            throw new IllegalArgumentException("Invalid request type. Expected ElectronicsRequest.");
        }
    }

    @Override
    public String generatePrompt(ItemDescriptionRequest request) {
        if (request instanceof ElectronicsRequest electronicsRequest) {
            return createElectronicsPrompt(electronicsRequest);
        } else {
            throw new IllegalArgumentException("Invalid request type. Expected ElectronicsRequest.");
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
