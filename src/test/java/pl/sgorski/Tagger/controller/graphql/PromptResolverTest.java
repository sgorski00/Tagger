package pl.sgorski.Tagger.controller.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;
import pl.sgorski.Tagger.exception.AiParsingException;
import pl.sgorski.Tagger.service.PromptService;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureGraphQlTester
class PromptResolverTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockitoBean
    private PromptService promptService;

    @MockitoBean
    private MessageSource messageSource;

    @Test
    void shouldReturnProductInfo() {
        PromptResponse response = new PromptResponse();
        response.setTitle("Casual T-Shirt");
        response.setDescription("A comfortable and stylish t-shirt for everyday wear.");
        response.setTags(new String[]{"#casual", "#t-shirt", "#young adults"});
        when(promptService.getResponse(any(PromptRequest.class))).thenReturn(response);

        String query = """
            query {
                generateListing(input: { responseStyle: "casual", item: "t-shirt", targetAudience: "young adults" }) {
                    title
                    description
                    tags
                }
            }
        """;

        graphQlTester.document(query)
                .execute()
                .path("generateListing")
                .hasValue()
                .path("generateListing.title")
                .entity(String.class)
                .isEqualTo("Casual T-Shirt")
                .path("generateListing.description")
                .entity(String.class)
                .isEqualTo("A comfortable and stylish t-shirt for everyday wear.")
                .path("generateListing.tags")
                .entityList(String.class)
                .containsExactly("#casual", "#t-shirt", "#young adults");
    }

    @Test
    void shouldReturnClothesInfo() {
        PromptResponse response = new PromptResponse();
        response.setTitle("Casual T-Shirt");
        when(promptService.getResponse(any(ClothesRequest.class))).thenReturn(response);

        String query = """
            query {
                generateListingClothes(input: { responseStyle: "casual", item: "t-shirt", targetAudience: "young adults" }) {
                    title
                }
            }
        """;

        graphQlTester.document(query)
                .execute()
                .path("generateListingClothes")
                .hasValue()
                .path("generateListingClothes.title")
                .entity(String.class)
                .isEqualTo("Casual T-Shirt");
    }

    @Test
    void shouldReturnElectronicsInfo() {
        PromptResponse response = new PromptResponse();
        response.setTitle("iPhone 16e 128GB");
        when(promptService.getResponse(any(ElectronicsRequest.class))).thenReturn(response);

        String query = """
            query {
                generateListingElectronics(input: { responseStyle: "casual", item: "iPhone 16e 128gb", targetAudience: "young adults" }) {
                    title
                }
            }
        """;

        graphQlTester.document(query)
                .execute()
                .path("generateListingElectronics")
                .hasValue()
                .path("generateListingElectronics.title")
                .entity(String.class)
                .isEqualTo("iPhone 16e 128GB");
    }

    @Test
    void shouldReturnErrorWhenParsingError() {
        when(promptService.getResponse(any(PromptRequest.class))).thenThrow(new AiParsingException("Parsing error"));
        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("Parsing error");
        String query = """
            query {
                generateListing(input: { responseStyle: "casual", item: "iPhone 16e 128gb", targetAudience: "young adults" }) {
                    title
                }
            }
        """;

        graphQlTester.document(query)
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertEquals(1, errors.size());
                    assertTrue(errors.getFirst().getMessage().contains("Parsing error"));
                });
    }
}