package pl.sgorski.Tagger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
import pl.sgorski.Tagger.mapper.ItemDescriptionMapper;
import pl.sgorski.Tagger.model.ItemDescription;
import pl.sgorski.Tagger.service.auth.UserService;

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

    public ItemDescriptionResponse getResponseAndSaveHistoryIfUserPresent(ItemDescriptionRequest request) {
        var result = getResponse(request);
        getPrincipalName().ifPresent(email -> createAndSaveItemHistory(result, email));
        return result;
    }

    private void createAndSaveItemHistory(ItemDescriptionResponse response, String requesterEmail) {
        var user = userService.findByEmail(requesterEmail);
        var itemDescription = mapper.toDescription(response, user);
        assignParentToTags(itemDescription);
        itemsHistoryService.save(itemDescription);
    }

    private void assignParentToTags(ItemDescription itemDescription) {
        if(!CollectionUtils.isEmpty(itemDescription.getTags())){
            itemDescription.getTags()
                    .forEach(tag -> tag.setItemDescription(itemDescription));
        }
    }

    private Optional<String> getPrincipalName() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken){
            log.debug("User not logged, cannot retrieve user name.");
            return Optional.empty();
        }
        return Optional.of(auth.getName());
    }
}
