package pl.sgorski.Tagger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.sgorski.Tagger.model.ItemDescription;
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

  public Page<ItemDescription> getHistory(Pageable pageable) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth instanceof AnonymousAuthenticationToken) {
      throw new AccessDeniedException("You must be logged in to access your items history.");
    }
    var user = userService.findByEmail(auth.getName());
    return itemDescriptionRepository.findAllByCreatedByOrderByIdDesc(user, pageable);
  }

  public ItemDescription getHistoryItem(Long id) {
    //TODO: implement domain exception and security check to prevent access to other users items
    return itemDescriptionRepository.findById(id)
      .orElseThrow();
  }
}
