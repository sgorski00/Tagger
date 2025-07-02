package pl.sgorski.Tagger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;

@Service
@RequiredArgsConstructor
public class ItemsServiceImpl implements ItemsService {

    private final AiService aiService;

    @Override
    public PromptResponse getFullInfo(PromptRequest request) {
        return aiService.generateResponse(generatePrompt(request));
    }

    @Override
    public String generatePrompt(PromptRequest request) {
        return generateBasePrompt(request);
    }
}
