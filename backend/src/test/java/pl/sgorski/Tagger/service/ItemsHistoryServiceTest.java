package pl.sgorski.Tagger.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.sgorski.Tagger.model.ItemDescription;
import pl.sgorski.Tagger.model.User;
import pl.sgorski.Tagger.repository.ItemDescriptionRepository;
import pl.sgorski.Tagger.service.auth.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemsHistoryServiceTest {

  @Mock
  private UserService userService;

  @Mock
  private ItemDescriptionRepository itemDescriptionRepository;

  @InjectMocks
  private ItemsHistoryService itemsHistoryService;

  private final String loggedInUserEmail = "user@example.com";

  @BeforeEach
  void setUp() {
    SecurityContextHolder.clearContext();
    var auth = new UsernamePasswordAuthenticationToken(loggedInUserEmail, null, null);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @Test
  void shouldSaveDescription() {
    itemsHistoryService.save(new ItemDescription());

    verify(itemDescriptionRepository, times(1)).save(any(ItemDescription.class));
  }

  @Test
  void shouldReturnHistoryPage_Page1Size10() {
    var pageRequest = PageRequest.of(0, 10);
    when(userService.findByEmail(any(String.class))).thenReturn(new User());
    when(itemDescriptionRepository.findAllByCreatedByOrderByIdDesc(any(User.class), any(PageRequest.class)))
      .thenReturn(new PageImpl<>(List.of(new ItemDescription(), new ItemDescription())));

    var result = itemsHistoryService.getHistory(pageRequest);

    assertNotNull(result);
    assertEquals(2, result.getContent().size());
    verify(userService, times(1)).findByEmail(anyString());
    verify(itemDescriptionRepository, times(1)).findAllByCreatedByOrderByIdDesc(any(User.class), any(Pageable.class));
  }

  @Test
  void shouldThrowWhileHistoryPage_UserNotFound() {
    var pageRequest = PageRequest.of(1, 10);
    when(userService.findByEmail(any(String.class))).thenThrow(new NoSuchElementException("User not found"));

    assertThrows(NoSuchElementException.class, () -> itemsHistoryService.getHistory(pageRequest));

    verify(userService, times(1)).findByEmail(anyString());
    verify(itemDescriptionRepository, never()).findAllByCreatedByOrderByIdDesc(any(User.class), any(Pageable.class));
  }

  @Test
  void shouldReturnSingleHistoryItem() {
    var user = new User();
    user.setEmail(loggedInUserEmail);
    var itemDescription = new ItemDescription();
    itemDescription.setId(1L);
    itemDescription.setCreatedBy(user);

    when(itemDescriptionRepository.findById(1L)).thenReturn(Optional.of(itemDescription));

    var result = itemsHistoryService.getHistoryItem(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());
    verify(itemDescriptionRepository, times(1)).findById(1L);
  }

  @Test
  void shouldThrowWhileGettingSingleHistoryItem_ItemNotFound() {
    when(itemDescriptionRepository.findById(999L)).thenReturn(Optional.empty());

    var thrown = assertThrows(NoSuchElementException.class, () -> itemsHistoryService.getHistoryItem(999L));

    assertTrue(thrown.getMessage().contains("Item description with id 999 not found"));
    verify(itemDescriptionRepository, times(1)).findById(999L);
  }

  @Test
  void shouldThrowWhileGettingSingleHistoryItem_UserNotOwner() {
    var differentUser = new User();
    differentUser.setEmail("different@example.com");
    var itemDescription = new ItemDescription();
    itemDescription.setId(1L);
    itemDescription.setCreatedBy(differentUser);

    when(itemDescriptionRepository.findById(1L)).thenReturn(Optional.of(itemDescription));

    var thrown = assertThrows(AccessDeniedException.class,
        () -> itemsHistoryService.getHistoryItem(1L));

    assertFalse(thrown.getMessage().isBlank());
    verify(itemDescriptionRepository, times(1)).findById(1L);
  }

  @Test
  void shouldThrowWhileGettingSingleHistoryItem_UserNotAuthenticated() {
    SecurityContextHolder.clearContext();

    var thrown = assertThrows(AccessDeniedException.class,
      () -> itemsHistoryService.getHistoryItem(1L));

    assertFalse(thrown.getMessage().isBlank());
    verify(itemDescriptionRepository, never()).findById(anyLong());
  }
}
