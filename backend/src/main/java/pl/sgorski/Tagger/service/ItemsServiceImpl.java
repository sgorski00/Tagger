package pl.sgorski.Tagger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;

@Service
@RequiredArgsConstructor
public class ItemsServiceImpl implements ItemsService {

    private final AiService aiService;

    @Override
    public ItemDescriptionResponse getFullInfo(ItemDescriptionRequest request) {
        return aiService.generateResponse(generatePrompt(request));
    }

    @Override
    public String generatePrompt(ItemDescriptionRequest request) {
        return generateBasePrompt(request);
    }
}
