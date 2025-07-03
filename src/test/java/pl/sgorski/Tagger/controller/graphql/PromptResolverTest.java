package pl.sgorski.Tagger.controller.graphql;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;
import pl.sgorski.Tagger.service.PromptService;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromptResolverTest {

    @Mock
    private PromptService promptService;

    @InjectMocks
    private PromptResolver promptResolver;

    @Test
    void shouldReturnProductInfo() {
        PromptRequest request = new PromptRequest();
        PromptResponse response = new PromptResponse();
        when(promptService.getResponse(request)).thenReturn(response);

        PromptResponse result = promptResolver.getProductInfo(request);

        assertSame(response, result);
        verify(promptService, times(1)).getResponse(request);
    }

    @Test
    void shouldReturnClothesInfo() {
        ClothesRequest request = new ClothesRequest();
        PromptResponse response = new PromptResponse();
        when(promptService.getResponse(request)).thenReturn(response);

        PromptResponse result = promptResolver.getClothesInfo(request);

        assertSame(response, result);
        verify(promptService, times(1)).getResponse(request);
    }

    @Test
    void shouldReturnElectronicsInfo() {
        ElectronicsRequest request = new ElectronicsRequest();
        PromptResponse response = new PromptResponse();
        when(promptService.getResponse(request)).thenReturn(response);

        PromptResponse result = promptResolver.getElectronicsInfo(request);

        assertSame(response, result);
        verify(promptService, times(1)).getResponse(request);
    }
}
