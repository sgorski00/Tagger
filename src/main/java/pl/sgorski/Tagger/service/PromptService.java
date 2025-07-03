package pl.sgorski.Tagger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;

@Service
@RequiredArgsConstructor
public class PromptService {

    private final ItemsServiceImpl itemsService;
    private final ElectronicsService electronicsService;
    private final ClothesService clothesService;

    public PromptResponse getResponse(PromptRequest request) {
        return switch (request) {
            case ElectronicsRequest electronicsRequest -> electronicsService.getFullInfo(electronicsRequest);
            case ClothesRequest clothesRequest -> clothesService.getFullInfo(clothesRequest);
            default -> itemsService.getFullInfo(request);
        };
    }
}
