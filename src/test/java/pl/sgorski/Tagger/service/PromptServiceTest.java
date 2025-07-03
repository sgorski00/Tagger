package pl.sgorski.Tagger.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;

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

    @InjectMocks
    private PromptService promptService;

    private PromptResponse response;

    @BeforeEach
    void setUp() {
        response = new PromptResponse();
        response.setTitle("Sample Title");
        response.setDescription("Sample Description");
        response.setTags(new String[]{"#tag1", "#tag2"});
    }

    @Test
    void shouldGetResponseForElectronicsRequest() {
        ElectronicsRequest request = new ElectronicsRequest();
        request.setItem("Laptop");
        request.setBrand("Dell");
        request.setModel("XPS 13");

        when(electronicsService.getFullInfo(request)).thenReturn(response);

        PromptResponse result = promptService.getResponse(request);

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

        when(clothesService.getFullInfo(request)).thenReturn(response);

        PromptResponse result = promptService.getResponse(request);

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
        PromptRequest request = new PromptRequest();
        request.setItem("Dress");
        request.setPlatform("ebay");
        request.setResponseStyle("detailed");

        when(itemsService.getFullInfo(request)).thenReturn(response);

        PromptResponse result = promptService.getResponse(request);

        assertNotNull(result);
        assertEquals("Sample Title", result.getTitle());
        assertEquals("Sample Description", result.getDescription());
        assertArrayEquals(new String[]{"#tag1", "#tag2"}, result.getTags());
        verify(electronicsService, never()).getFullInfo(any());
        verify(itemsService, times(1)).getFullInfo(any(PromptRequest.class));
        verify(clothesService, never()).getFullInfo(any());
    }

    @Test
    void shouldThrowWhenNull() {
        assertThrows(NullPointerException.class, () -> promptService.getResponse(null));

        verify(electronicsService, never()).getFullInfo(any());
        verify(itemsService, never()).getFullInfo(any());
        verify(clothesService, never()).getFullInfo(any());
    }
}
