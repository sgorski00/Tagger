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
import pl.sgorski.Tagger.service.auth.UserService;

import java.security.Principal;
import java.util.NoSuchElementException;

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

    @Mock
    private Principal principal;

    private ItemDescriptionResponse response;

    @BeforeEach
    void setUp() {
        response = new ItemDescriptionResponse();
        response.setTitle("Sample Title");
        response.setDescription("Sample Description");
        response.setTags(new String[]{"#tag1", "#tag2"});

        when(principal.getName()).thenReturn("testUser");
    }

    @Test
    void shouldGetResponseForElectronicsRequest() {
        ElectronicsRequest request = new ElectronicsRequest();
        request.setItem("Laptop");
        request.setBrand("Dell");
        request.setModel("XPS 13");

        when(userService.findByEmail("testUser")).thenThrow(new NoSuchElementException("User not found"));
        when(electronicsService.getFullInfo(request)).thenReturn(response);

        ItemDescriptionResponse result = promptService.getResponseAndSaveHistory(request, principal);

        assertNotNull(result);
        assertEquals("Sample Title", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
        assertArrayEquals(new String[]{"#tag1", "#tag2"}, result.getTags());
        verify(electronicsService, times(1)).getFullInfo(any(ElectronicsRequest.class));
        verify(itemsService, never()).getFullInfo(any());
        verify(clothesService, never()).getFullInfo(any());
    }

    @Test
    void shouldGetResponseForClothesRequest() {
        ClothesRequest request = new ClothesRequest();
        request.setItem("Dress");
        request.setSize("XL");
        request.setMaterial("Cotton");

        when(userService.findByEmail("testUser")).thenThrow(new NoSuchElementException("User not found"));
        when(clothesService.getFullInfo(request)).thenReturn(response);

        ItemDescriptionResponse result = promptService.getResponseAndSaveHistory(request, principal);

        assertNotNull(result);
        assertEquals("Sample Title", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
        assertArrayEquals(new String[]{"#tag1", "#tag2"}, result.getTags());
        verify(electronicsService, never()).getFullInfo(any());
        verify(itemsService, never()).getFullInfo(any());
        verify(clothesService, times(1)).getFullInfo(any(ClothesRequest.class));
    }

    @Test
    void shouldGetResponseForGeneralRequest() {
        ItemDescriptionRequest request = new ItemDescriptionRequest();
        request.setItem("Dress");
        request.setPlatform("ebay");
        request.setResponseStyle("detailed");

        when(userService.findByEmail("testUser")).thenThrow(new NoSuchElementException("User not found"));
        when(itemsService.getFullInfo(request)).thenReturn(response);

        ItemDescriptionResponse result = promptService.getResponseAndSaveHistory(request, principal);

        assertNotNull(result);
        assertEquals("Sample Title", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
        assertArrayEquals(new String[]{"#tag1", "#tag2"}, result.getTags());
        verify(electronicsService, never()).getFullInfo(any());
        verify(itemsService, times(1)).getFullInfo(any(ItemDescriptionRequest.class));
        verify(clothesService, never()).getFullInfo(any());
    }

    @Test
    void shouldThrowWhenRequestIsNull() {
        assertThrows(NullPointerException.class, () -> promptService.getResponseAndSaveHistory(null, principal));

        verify(electronicsService, never()).getFullInfo(any());
        verify(itemsService, never()).getFullInfo(any());
        verify(clothesService, never()).getFullInfo(any());
    }
}
