package pl.sgorski.Tagger.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;

@Service
@RequiredArgsConstructor
public class ElectronicsService implements ItemsService {

    private final AiService aiService;

    @Override
    public PromptResponse getFullInfo(PromptRequest request) {
        if (request instanceof ElectronicsRequest electronicsRequest) {
            return aiService.generateResponse(generatePrompt(electronicsRequest));
        } else {
            throw new IllegalArgumentException("Invalid request type. Expected ElectronicsRequest.");
        }
    }

    @Override
    public String generatePrompt(PromptRequest request) {
        if(request instanceof ElectronicsRequest electronicsRequest) {
            return createElectronicsPrompt(electronicsRequest);
        } else {
            throw new IllegalArgumentException("Invalid request type. Expected ElectronicsRequest.");
        }
    }

    private String createElectronicsPrompt(ElectronicsRequest request) {
        return String.format(
                "%sDescribe the electronic item: brand: %s, model: %s, specs: %s, color: %s, months of warranty: %s.",
                generateBasePrompt(request),
                request.getBrand(),
                request.getModel(),
                request.getSpecifications(),
                request.getColor(),
                request.getMonthsOfWarranty()
        );
    }
}
