package pl.sgorski.Tagger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
import pl.sgorski.Tagger.mapper.ItemDescriptionMapper;
import pl.sgorski.Tagger.model.ItemDescription;
import pl.sgorski.Tagger.model.User;
import pl.sgorski.Tagger.service.auth.UserService;

import java.security.Principal;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class PromptService {

    private final ItemsServiceImpl itemsService;
    private final ElectronicsService electronicsService;
    private final ClothesService clothesService;
    private final ItemsHistoryService itemsHistoryService;
    private final ItemDescriptionMapper mapper;
    private final UserService userService;

    private ItemDescriptionResponse getResponse(ItemDescriptionRequest request) {
        return switch (request) {
            case ElectronicsRequest electronicsRequest -> electronicsService.getFullInfo(electronicsRequest);
            case ClothesRequest clothesRequest -> clothesService.getFullInfo(clothesRequest);
            default -> itemsService.getFullInfo(request);
        };
    }

    public ItemDescriptionResponse getResponseAndSaveHistoryIfUserPresent(ItemDescriptionRequest request, Principal principal) {
        ItemDescriptionResponse result = getResponse(request);
        getPrincipalName(principal).ifPresent(email -> createAndSaveItemHistory(result, email));
        return result;
    }

    private void createAndSaveItemHistory(ItemDescriptionResponse response, String requesterEmail) {
        User user = userService.findByEmail(requesterEmail);
        ItemDescription itemDescription = mapper.toDescription(response, user);
        assignParentToTags(itemDescription);
        itemsHistoryService.save(itemDescription);
    }

    private void assignParentToTags(ItemDescription itemDescription) {
        if(!CollectionUtils.isEmpty(itemDescription.getTags())){
            itemDescription.getTags()
                    .forEach(tag -> tag.setItemDescription(itemDescription));
        }
    }

    private Optional<String> getPrincipalName(Principal principal) {
        return Optional.ofNullable(principal)
                .map(Principal::getName)
                .or(() -> {
                    log.debug("User not logged, cannot retrieve user name.");
                    return Optional.empty();
                });
    }
}
