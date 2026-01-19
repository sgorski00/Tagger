package pl.sgorski.Tagger.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.sgorski.Tagger.model.ItemDescription;
import pl.sgorski.Tagger.model.User;
import pl.sgorski.Tagger.repository.ItemDescriptionRepository;
import pl.sgorski.Tagger.service.auth.UserService;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Test
    void shouldSaveDescription() {
        itemsHistoryService.save(new ItemDescription());

        verify(itemDescriptionRepository, times(1)).save(any(ItemDescription.class));
    }

    @Test
    void shouldReturnHistoryPage_Page1Size10() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(userService.findByEmail(any(String.class))).thenReturn(new User());
        when(itemDescriptionRepository.findAllByCreatedByOrderByIdDesc(any(User.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(new ItemDescription(), new ItemDescription())));

        var result = itemsHistoryService.getHistory("test@email.com", pageRequest);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(userService, times(1)).findByEmail(anyString());
        verify(itemDescriptionRepository, times(1)).findAllByCreatedByOrderByIdDesc(any(User.class), any(Pageable.class));
    }

    @Test
    void shouldThrowWhileHistoryPage_UserNotFound() {
        PageRequest pageRequest = PageRequest.of(1, 10);
        when(userService.findByEmail(any(String.class))).thenThrow(new NoSuchElementException("User not found"));

        assertThrows(NoSuchElementException.class, () -> itemsHistoryService.getHistory("test@email.com", pageRequest));

        verify(userService, times(1)).findByEmail(anyString());
        verify(itemDescriptionRepository, never()).findAllByCreatedByOrderByIdDesc(any(User.class), any(Pageable.class));
    }
}
