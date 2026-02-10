package pl.sgorski.Tagger.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ElectronicsServiceTest {

    @Mock
    private AiService aiService;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private ElectronicsService electronicsService;

    @Test
    void shouldReturnResponse_getFullInfo() {
        var request = new ElectronicsRequest();
        when(aiService.generateResponse(any(String.class))).thenReturn(new ItemDescriptionResponse());

        var result = electronicsService.getFullInfo(request);

        assertNotNull(result);
    }

    @Test
    void shouldThrow_getFullInfo_WrongRequest() {
        var request = new ItemDescriptionRequest();

        assertThrows(IllegalArgumentException.class, () -> electronicsService.getFullInfo(request));
    }

    @Test
    void shouldReturnPrompt_generatePrompt() {
        var request = new ElectronicsRequest();
        request.setPlatform("eBay");
        request.setBrand("Samsung");
        request.setModel("Galaxy S21");

        var result = electronicsService.generatePrompt(request);

        assertFalse(result.isBlank());
        assertTrue(result.contains("electronic item"));
        assertTrue(result.contains("Samsung"));
        assertTrue(result.contains("Galaxy S21"));
        assertTrue(result.contains("eBay"));
    }

    @Test
    void shouldThrow_generatePrompt_WrongRequest() {
        var request = new ItemDescriptionRequest();

        assertThrows(IllegalArgumentException.class, () -> electronicsService.generatePrompt(request));
    }
}
