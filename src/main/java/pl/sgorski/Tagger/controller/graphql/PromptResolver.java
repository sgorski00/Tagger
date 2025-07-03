package pl.sgorski.Tagger.controller.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;
import pl.sgorski.Tagger.service.PromptService;

@Controller
@RequiredArgsConstructor
public class PromptResolver {

    private final PromptService promptService;

    @QueryMapping(name = "generateListing")
    public PromptResponse getProductInfo(@Argument("input") PromptRequest request) {
        return promptService.getInfo(request);
    }

    @QueryMapping(name = "generateListingClothes")
    public PromptResponse getClothesInfo(@Argument("input") ClothesRequest request) {
        return promptService.getInfo(request);
    }

    @QueryMapping(name = "generateListingElectronics")
    public PromptResponse getElectronicsInfo(@Argument("input") ElectronicsRequest request) {
        return promptService.getInfo(request);
    }
}
