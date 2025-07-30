package pl.sgorski.Tagger.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
import pl.sgorski.Tagger.mapper.ItemDescriptionMapper;
import pl.sgorski.Tagger.model.ItemDescription;
import pl.sgorski.Tagger.model.Tag;
import pl.sgorski.Tagger.model.User;
import pl.sgorski.Tagger.service.auth.UserService;

import java.security.Principal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromptServiceTest {

    @Mock
    private ItemsServiceImpl itemsService;
    @Mock
    private ElectronicsService electronicsService;
    @Mock
    private ClothesService clothesService;
    @Mock
    private ItemsHistoryService itemsHistoryService;
    @Mock
    private ItemDescriptionMapper mapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private PromptService promptService;

    private ItemDescriptionResponse response;

    @BeforeEach
    void setUp() {
        response = new ItemDescriptionResponse();
        response.setTitle("Sample Title");
        response.setDescription("Sample Description");
        response.setTags(new String[]{"#tag1", "#tag2"});
    }

    @Test
    void shouldGetResponseForElectronicsRequest_NotLogged() {
        ElectronicsRequest request = new ElectronicsRequest();
        request.setItem("Laptop");
        request.setBrand("Dell");
        request.setModel("XPS 13");

        when(electronicsService.getFullInfo(any(ElectronicsRequest.class))).thenReturn(response);

        ItemDescriptionResponse result = promptService.getResponseAndSaveHistoryIfUserPresent(request, null);

        assertNotNull(result);
        assertEquals("Sample Title", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
        assertArrayEquals(new String[]{"#tag1", "#tag2"}, result.getTags());
        verify(electronicsService, times(1)).getFullInfo(any(ElectronicsRequest.class));
        verify(itemsService, never()).getFullInfo(any());
        verify(clothesService, never()).getFullInfo(any());
        verify(itemsHistoryService, never()).save(any());
    }

    @Test
    void shouldGetResponseForClothesRequest_NotLogged() {
        ClothesRequest request = new ClothesRequest();
        request.setItem("Dress");
        request.setSize("XL");
        request.setMaterial("Cotton");

        when(clothesService.getFullInfo(any(ClothesRequest.class))).thenReturn(response);

        ItemDescriptionResponse result = promptService.getResponseAndSaveHistoryIfUserPresent(request, null);

        assertNotNull(result);
        assertEquals("Sample Title", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
        assertArrayEquals(new String[]{"#tag1", "#tag2"}, result.getTags());
        verify(electronicsService, never()).getFullInfo(any());
        verify(itemsService, never()).getFullInfo(any());
        verify(clothesService, times(1)).getFullInfo(any(ClothesRequest.class));
        verify(itemsHistoryService, never()).save(any());
    }

    @Test
    void shouldGetResponseForGeneralRequest_NotLogged() {
        ItemDescriptionRequest request = new ItemDescriptionRequest();
        request.setItem("Dress");
        request.setPlatform("ebay");
        request.setResponseStyle("detailed");

        when(itemsService.getFullInfo(any(ItemDescriptionRequest.class))).thenReturn(response);

        ItemDescriptionResponse result = promptService.getResponseAndSaveHistoryIfUserPresent(request, null);

        assertNotNull(result);
        assertEquals("Sample Title", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
        assertArrayEquals(new String[]{"#tag1", "#tag2"}, result.getTags());
        verify(electronicsService, never()).getFullInfo(any());
        verify(itemsService, times(1)).getFullInfo(any(ItemDescriptionRequest.class));
        verify(clothesService, never()).getFullInfo(any());
        verify(itemsHistoryService, never()).save(any());
    }

    @Test
    void shouldThrowWhenRequestIsNull_NotLogged() {
        assertThrows(NullPointerException.class, () -> promptService.getResponseAndSaveHistoryIfUserPresent(null, null));

        verify(electronicsService, never()).getFullInfo(any());
        verify(itemsService, never()).getFullInfo(any());
        verify(clothesService, never()).getFullInfo(any());
        verify(itemsHistoryService, never()).save(any());
    }

    @Test
    void shouldGetResponseForElectronicsRequest_LoggedAndNoTags() {
        ElectronicsRequest request = new ElectronicsRequest();
        request.setItem("Laptop");
        request.setBrand("Dell");
        request.setModel("XPS 13");
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@email.com");
        when(userService.findByEmail(anyString())).thenReturn(new User());
        when(mapper.toDescription(any(ItemDescriptionResponse.class), any(User.class))).thenReturn(new ItemDescription());
        when(electronicsService.getFullInfo(any(ElectronicsRequest.class))).thenReturn(response);

        ItemDescriptionResponse result = promptService.getResponseAndSaveHistoryIfUserPresent(request, principal);

        assertNotNull(result);
        assertEquals("Sample Title", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
        assertArrayEquals(new String[]{"#tag1", "#tag2"}, result.getTags());
        verify(electronicsService, times(1)).getFullInfo(any(ElectronicsRequest.class));
        verify(itemsService, never()).getFullInfo(any());
        verify(clothesService, never()).getFullInfo(any());
        verify(itemsHistoryService, times(1)).save(any());
    }

    @Test
    void shouldGetResponseForElectronicsRequest_LoggedAndWithTags() {
        ElectronicsRequest request = new ElectronicsRequest();
        request.setItem("Laptop");
        request.setBrand("Dell");
        request.setModel("XPS 13");
        Set<Tag> tags = Set.of(new Tag("#tag1"), new Tag("#tag2"));
        ItemDescription itemDescription = new ItemDescription();
        itemDescription.setTags(tags);

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@email.com");
        when(userService.findByEmail(anyString())).thenReturn(new User());
        when(mapper.toDescription(any(ItemDescriptionResponse.class), any(User.class))).thenReturn(itemDescription);
        when(electronicsService.getFullInfo(any(ElectronicsRequest.class))).thenReturn(response);

        ItemDescriptionResponse result = promptService.getResponseAndSaveHistoryIfUserPresent(request, principal);

        assertNotNull(result);
        assertEquals("Sample Title", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
        assertArrayEquals(new String[]{"#tag1", "#tag2"}, result.getTags());
        assertAll(itemDescription.getTags().stream()
                .map(tag -> () -> assertNotNull(tag.getName())));
        verify(electronicsService, times(1)).getFullInfo(any(ElectronicsRequest.class));
        verify(itemsService, never()).getFullInfo(any());
        verify(clothesService, never()).getFullInfo(any());
        verify(itemsHistoryService, times(1)).save(any());
    }
}
