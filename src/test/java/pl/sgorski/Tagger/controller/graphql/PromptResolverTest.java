package pl.sgorski.Tagger.controller.graphql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
import pl.sgorski.Tagger.mapper.ItemDescriptionMapper;
import pl.sgorski.Tagger.model.ItemDescription;
import pl.sgorski.Tagger.service.ItemsHistoryService;
import pl.sgorski.Tagger.service.PromptService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@GraphQlTest(PromptResolver.class)
@AutoConfigureGraphQlTester
@ActiveProfiles("test")
public class PromptResolverTest {

    @Autowired
    private GraphQlTester tester;

    @MockitoBean
    private PromptService promptService;

    @MockitoBean
    private ItemsHistoryService itemsHistoryService;

    @MockitoBean
    private ItemDescriptionMapper mapper;

    private ItemDescriptionResponse response;

    @BeforeEach
    void setUp() {
        response = new ItemDescriptionResponse();
        response.setTitle("Test Title");
        response.setDescription("Test Description");
        response.setTags(new String[]{"#tag1", "#tag2", "#tag3"});

        var auth = new UsernamePasswordAuthenticationToken("testUser", "password");
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void shouldReturnProductInfo() {
        String query = """
                    query {
                        generateListing(input: { item: "Test Item", responseStyle: "casual", targetAudience: "young adults", tagsQuantity: 10, platform: "ebay" }) {
                            title
                            description
                            tags
                        }
                    }
                """;

        when(promptService.getResponseAndSaveHistory(any(ItemDescriptionRequest.class), any(Principal.class)))
                .thenReturn(response);

        tester.document(query)
                .execute()
                .path("generateListing.title").entity(String.class).isEqualTo("Test Title")
                .path("generateListing.description").entity(String.class).isEqualTo("Test Description")
                .path("generateListing.tags").entityList(String.class).hasSize(3);

        verify(promptService, times(1)).getResponseAndSaveHistory(any(ItemDescriptionRequest.class), any(Principal.class));
    }

    @Test
    void shouldReturnClothesInfo() {
        String query = """
                    query {
                        generateListingClothes(input: { item: "Test Item", responseStyle: "casual", targetAudience: "young adults", tagsQuantity: 10, platform: "ebay" }) {
                            title
                            description
                            tags
                        }
                    }
                """;

        when(promptService.getResponseAndSaveHistory(any(ClothesRequest.class), any(Principal.class)))
                .thenReturn(response);

        tester.document(query)
                .execute()
                .path("generateListingClothes.title").entity(String.class).isEqualTo("Test Title")
                .path("generateListingClothes.description").entity(String.class).isEqualTo("Test Description")
                .path("generateListingClothes.tags").entityList(String.class).hasSize(3);

        verify(promptService, times(1)).getResponseAndSaveHistory(any(ItemDescriptionRequest.class), any(Principal.class));
    }

    @Test
    void shouldReturnElectronicsInfo() {
        String query = """
                    query {
                        generateListingElectronics(input: { item: "Test Item", responseStyle: "casual", targetAudience: "young adults", tagsQuantity: 10, platform: "ebay" }) {
                            title
                            description
                            tags
                        }
                    }
                """;

        when(promptService.getResponseAndSaveHistory(any(ElectronicsRequest.class), any(Principal.class)))
                .thenReturn(response);

        tester.document(query)
                .execute()
                .path("generateListingElectronics.title").entity(String.class).isEqualTo("Test Title")
                .path("generateListingElectronics.description").entity(String.class).isEqualTo("Test Description")
                .path("generateListingElectronics.tags").entityList(String.class).hasSize(3);

        verify(promptService, times(1)).getResponseAndSaveHistory(any(ItemDescriptionRequest.class), any(Principal.class));
    }

    @Test
    void shouldReturnHistory() {
        String query = """
                    query {
                        getHistory {
                            title
                            description
                        }
                    }
                """;
        Page<ItemDescription> page = new PageImpl<>(Arrays.asList(new ItemDescription(), new ItemDescription()));

        when(itemsHistoryService.getHistory(anyString(), any(Pageable.class))).thenReturn(page);
        when(mapper.toResponse(any(ItemDescription.class))).thenReturn(response);

        tester.document(query)
                .execute()
                .path("getHistory").entityList(ItemDescriptionResponse.class).hasSize(2)
                .path("getHistory[1].title").entity(String.class).isEqualTo("Test Title")
                .path("getHistory[1].description").entity(String.class).isEqualTo("Test Description");

        verify(itemsHistoryService, times(1)).getHistory(anyString(), any(Pageable.class));
    }

    @Test
    void shouldNotReturnHistory_NotAuthenticated() {
        SecurityContextHolder.clearContext();
        String query = """
                    query {
                        getHistory {
                            title
                            description
                        }
                    }
                """;

        tester.document(query)
                .execute()
                .errors()
                .satisfy(err -> assertEquals(1, err.size()));
        verify(itemsHistoryService, times(0)).getHistory(anyString(), any(Pageable.class));
    }
}
