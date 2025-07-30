package pl.sgorski.Tagger.controller.graphql;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
import pl.sgorski.Tagger.mapper.ItemDescriptionMapper;
import pl.sgorski.Tagger.service.ItemsHistoryService;
import pl.sgorski.Tagger.service.PromptService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PromptResolver {

    private final PromptService promptService;
    private final ItemsHistoryService itemsHistoryService;
    private final ItemDescriptionMapper mapper;

    @QueryMapping(name = "generateListing")
    public ItemDescriptionResponse getProductInfo(@Argument("input") @Valid ItemDescriptionRequest request, Principal principal) {
        return promptService.getResponseAndSaveHistoryIfUserPresent(request, principal);
    }

    @QueryMapping(name = "generateListingClothes")
    public ItemDescriptionResponse getClothesInfo(@Argument("input") @Valid ClothesRequest request, Principal principal) {
        return promptService.getResponseAndSaveHistoryIfUserPresent(request, principal);
    }

    @QueryMapping(name = "generateListingElectronics")
    public ItemDescriptionResponse getElectronicsInfo(@Argument("input") @Valid ElectronicsRequest request, Principal principal) {
        return promptService.getResponseAndSaveHistoryIfUserPresent(request, principal);
    }

    @QueryMapping(name = "getHistory")
    @PreAuthorize("isAuthenticated()")
    public List<ItemDescriptionResponse> getHistory(
            @Argument("page") int page,
            @Argument("size") int size,
            Principal principal
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        var result = itemsHistoryService.getHistory(principal.getName(), pageRequest);
        return result.map(mapper::toResponse).getContent();
    }
}
