package pl.sgorski.Tagger.controller.graphql;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
import pl.sgorski.Tagger.service.PromptService;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

//TODO: Repair tests (+ use graphqltest instead of mockito extension)
@ExtendWith(MockitoExtension.class)
public class PromptResolverTest {

    @Mock
    private PromptService promptService;

    @Mock
    private Principal principal;

    @InjectMocks
    private PromptResolver promptResolver;

    @Test
    void shouldReturnProductInfo() {
        ItemDescriptionResponse response = new ItemDescriptionResponse();
        when(promptService.getResponseAndSaveHistory(any(ItemDescriptionRequest.class), any(Principal.class))).thenReturn(response);
        when(principal.getName()).thenReturn("testUser");

        ItemDescriptionResponse result = promptResolver.getProductInfo(new ItemDescriptionRequest(), principal);

        assertSame(response, result);
        verify(promptService, times(1)).getResponseAndSaveHistory(any(ItemDescriptionRequest.class), any(Principal.class));
    }

    @Test
    void shouldReturnClothesInfo() {
        ItemDescriptionResponse response = new ItemDescriptionResponse();
        when(promptService.getResponseAndSaveHistory(any(ItemDescriptionRequest.class), any(Principal.class))).thenReturn(response);
        when(principal.getName()).thenReturn("testUser");

        ItemDescriptionResponse result = promptResolver.getClothesInfo(new ClothesRequest(), principal);

        assertSame(response, result);
        verify(promptService, times(1)).getResponseAndSaveHistory(any(ItemDescriptionRequest.class), any(Principal.class));
    }

    @Test
    void shouldReturnElectronicsInfo() {
        ItemDescriptionResponse response = new ItemDescriptionResponse();
        when(promptService.getResponseAndSaveHistory(any(ItemDescriptionRequest.class), any(Principal.class))).thenReturn(response);
        when(principal.getName()).thenReturn("testUser");

        ItemDescriptionResponse result = promptResolver.getElectronicsInfo(new ElectronicsRequest(), principal);

        assertSame(response, result);
        verify(promptService, times(1)).getResponseAndSaveHistory(any(ItemDescriptionRequest.class), any(Principal.class));
    }
}
