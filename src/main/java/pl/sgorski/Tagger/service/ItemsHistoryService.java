package pl.sgorski.Tagger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.model.ItemDescription;
import pl.sgorski.Tagger.model.User;
import pl.sgorski.Tagger.repository.ItemDescriptionRepository;
import pl.sgorski.Tagger.service.auth.UserService;

@Log4j2
@Service
@RequiredArgsConstructor
public class ItemsHistoryService {

    private final UserService userService;
    private final ItemDescriptionRepository itemDescriptionRepository;

    public void save(ItemDescription itemDescription) {
        log.debug("Saving new item description");
        itemDescriptionRepository.save(itemDescription);
    }

    public Page<ItemDescription> getHistory(String requesterEmail, Pageable pageable) {
        User user = userService.findByEmail(requesterEmail);
        return itemDescriptionRepository.findAllByCreatedByOrderByIdDesc(user, pageable);
    }
}
