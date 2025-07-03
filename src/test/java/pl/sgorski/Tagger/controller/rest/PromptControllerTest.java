package pl.sgorski.Tagger.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.dto.PromptResponse;
import pl.sgorski.Tagger.exception.AiParsingException;
import pl.sgorski.Tagger.service.PromptService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class PromptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PromptService promptService;

    PromptResponse response;

    @BeforeEach
    void setUp() {
        response = new PromptResponse();
        response.setTitle("Test Item");
        response.setDescription("This is a test item description.");
        response.setTags(new String[]{"#test", "#item"});
    }

    @Test
    void shouldReturnResponse_getInfo_AllInfo() throws Exception {
        PromptRequest request = new PromptRequest();
        request.setItem("Test Item");
        request.setPlatform("Allegro");
        request.setResponseStyle("formal");
        request.setTargetAudience("young adults");
        request.setTagsQuantity(10);

        when(promptService.getResponse(request)).thenReturn(response);

        mockMvc.perform(post("/api/tags")
                        .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    PromptResponse resultResponse = objectMapper.readValue(result.getResponse().getContentAsString(), PromptResponse.class);
                    assertEquals("Test Item", resultResponse.getTitle());
                    assertEquals("This is a test item description.", resultResponse.getDescription());
                    assertEquals(2, resultResponse.getTags().length);
                });
        verify(promptService, times(1)).getResponse(any(PromptRequest.class));
    }

    @Test
    void shouldReturnProblemDetail_getInfo_EmptyRequest() throws Exception {
        PromptRequest request = new PromptRequest();

        when(promptService.getResponse(request)).thenReturn(response);

        mockMvc.perform(post("/api/tags")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(result -> {
                    ProblemDetail problemDetail = objectMapper.readValue(result.getResponse().getContentAsString(), ProblemDetail.class);
                    assertNotNull(problemDetail);
                    assertEquals(HttpStatus.CONFLICT.value(), problemDetail.getStatus());
                    assertNotNull(problemDetail.getDetail());
                    assertFalse(problemDetail.getDetail().isBlank());
                    assertEquals("Not valid data", problemDetail.getTitle());
                });
    }

    @Test
    void shouldReturnProblemDetail_getInfo_NotValidFormatInTagsQuantity() throws Exception {
        mockMvc.perform(post("/api/tags")
                        .contentType("application/json")
                        .content("{\"item\":\"test item\",\"platform\":\"olx\",\"responseStyle\":\"formal\",\"targetAudience\":\"adults\",\"tagsQuantity\":\"not_valid\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ProblemDetail problemDetail = objectMapper.readValue(result.getResponse().getContentAsString(), ProblemDetail.class);
                    assertNotNull(problemDetail);
                    assertEquals(HttpStatus.BAD_REQUEST.value(), problemDetail.getStatus());
                    assertNotNull(problemDetail.getDetail());
                    assertFalse(problemDetail.getDetail().isBlank());
                    assertEquals("Invalid Format", problemDetail.getTitle());
                });
    }

    @Test
    void shouldReturnResponse_getClothesInfo_AllInfo() throws Exception {
        ClothesRequest request = new ClothesRequest();
        request.setItem("Test Item");
        request.setPlatform("Allegro");
        request.setResponseStyle("formal");
        request.setTargetAudience("young adults");
        request.setTagsQuantity(10);
        request.setColor("Red");
        request.setMaterial("Cotton");
        request.setSize("M");

        when(promptService.getResponse(request)).thenReturn(response);

        mockMvc.perform(post("/api/tags/clothes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    PromptResponse resultResponse = objectMapper.readValue(result.getResponse().getContentAsString(), PromptResponse.class);
                    assertEquals("Test Item", resultResponse.getTitle());
                    assertEquals("This is a test item description.", resultResponse.getDescription());
                    assertEquals(2, resultResponse.getTags().length);
                });
        verify(promptService, times(1)).getResponse(any(PromptRequest.class));
    }

    @Test
    void shouldReturnProblemDetails_getClothesInfo_UnexpectedException() throws Exception {
        ClothesRequest request = new ClothesRequest();
        request.setItem("Test Item");
        request.setPlatform("Allegro");
        request.setResponseStyle("formal");
        request.setTargetAudience("young adults");
        request.setTagsQuantity(10);
        request.setColor("Red");
        request.setMaterial("Cotton");
        request.setSize("M");

        when(promptService.getResponse(request)).thenThrow(new RuntimeException("Something wrong happened"));

        mockMvc.perform(post("/api/tags/clothes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> {
                    ProblemDetail problemDetail = objectMapper.readValue(result.getResponse().getContentAsString(), ProblemDetail.class);
                    assertNotNull(problemDetail);
                    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), problemDetail.getStatus());
                    assertEquals("Something wrong happened", problemDetail.getDetail());
                    assertEquals("Unexpected Error", problemDetail.getTitle());
                });
        verify(promptService, times(1)).getResponse(any(PromptRequest.class));
    }

    @Test
    void shouldReturnResponse_getElectronicsInfo_AllInfo() throws Exception {
        ElectronicsRequest request = new ElectronicsRequest();
        request.setItem("Test Item");
        request.setPlatform("Allegro");
        request.setResponseStyle("formal");
        request.setTargetAudience("young adults");
        request.setTagsQuantity(10);
        request.setColor("Red");
        request.setBrand("Cotton");
        request.setModel("M");
        request.setMonthsOfWarranty(12);
        request.setSpecifications("Test specifications");

        when(promptService.getResponse(request)).thenReturn(response);

        mockMvc.perform(post("/api/tags/electronics")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    PromptResponse resultResponse = objectMapper.readValue(result.getResponse().getContentAsString(), PromptResponse.class);
                    assertEquals("Test Item", resultResponse.getTitle());
                    assertEquals("This is a test item description.", resultResponse.getDescription());
                    assertEquals(2, resultResponse.getTags().length);
                });
        verify(promptService, times(1)).getResponse(any(PromptRequest.class));
    }

    @Test
    void shouldReturnProblemDetails_getElectronicsInfo_ParsingException() throws Exception {
        ElectronicsRequest request = new ElectronicsRequest();
        request.setItem("Test Item");
        request.setPlatform("Allegro");
        request.setResponseStyle("formal");
        request.setTargetAudience("young adults");
        request.setTagsQuantity(10);
        request.setColor("Red");
        request.setBrand("Cotton");
        request.setModel("M");
        request.setMonthsOfWarranty(12);
        request.setSpecifications("Test specifications");

        when(promptService.getResponse(request)).thenThrow(new AiParsingException("Parsing error"));

        mockMvc.perform(post("/api/tags/electronics")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ProblemDetail problemDetail = objectMapper.readValue(result.getResponse().getContentAsString(), ProblemDetail.class);
                    assertNotNull(problemDetail);
                    assertEquals(HttpStatus.BAD_REQUEST.value(), problemDetail.getStatus());
                    assertNotNull(problemDetail.getDetail());
                    assertFalse(problemDetail.getDetail().isBlank());
                    assertEquals("Could not parse response", problemDetail.getTitle());
                });
        verify(promptService, times(1)).getResponse(any(PromptRequest.class));
    }
}
