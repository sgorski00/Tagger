package pl.sgorski.Tagger.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
import pl.sgorski.Tagger.exception.AiParsingException;
import pl.sgorski.Tagger.mapper.ItemDescriptionMapper;
import pl.sgorski.Tagger.model.ItemDescription;
import pl.sgorski.Tagger.service.ItemsHistoryService;
import pl.sgorski.Tagger.service.PromptService;
import pl.sgorski.Tagger.service.auth.JwtService;
import pl.sgorski.Tagger.service.auth.UserService;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemDescriptionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ItemDescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private ItemsHistoryService itemsHistoryService;

    @MockitoBean
    private ItemDescriptionMapper itemDescriptionMapper;

    @MockitoBean
    private PromptService promptService;

    private ItemDescriptionResponse response;

    @BeforeEach
    void setUp() {
        response = new ItemDescriptionResponse();
        response.setTitle("Test Item");
        response.setDescription("This is a test item description.");
        response.setTags(new String[]{"#test", "#item"});
    }

    @Test
    void shouldReturnResponse_getInfo_AllInfo() throws Exception {
        ItemDescriptionRequest request = new ItemDescriptionRequest();
        request.setItem("Test Item");
        request.setPlatform("Allegro");
        request.setResponseStyle("formal");
        request.setTargetAudience("young adults");
        request.setTagsQuantity(10);

        when(promptService.getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), nullable(Principal.class))).thenReturn(response);

        mockMvc.perform(post("/api/tags")
                        .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    ItemDescriptionResponse resultResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ItemDescriptionResponse.class);
                    assertEquals("Test Item", resultResponse.getTitle());
                    assertEquals("This is a test item description.", resultResponse.getDescription());
                    assertEquals(2, resultResponse.getTags().length);
                });
        verify(promptService, times(1)).getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), nullable(Principal.class));
    }

    @Test
    void shouldReturnResponseAndSaveHistory_getInfo_AllInfo() throws Exception {
        ItemDescriptionRequest request = new ItemDescriptionRequest();
        request.setItem("Test Item");
        request.setPlatform("Allegro");
        request.setResponseStyle("formal");
        request.setTargetAudience("young adults");
        request.setTagsQuantity(10);

        when(promptService.getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), any(Principal.class))).thenReturn(response);

        mockMvc.perform(post("/api/tags")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .principal(() -> "testUser"))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ItemDescriptionResponse resultResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ItemDescriptionResponse.class);
                    assertEquals("Test Item", resultResponse.getTitle());
                    assertEquals("This is a test item description.", resultResponse.getDescription());
                    assertEquals(2, resultResponse.getTags().length);
                });
        verify(promptService, times(1)).getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), any(Principal.class));
    }

    @Test
    void shouldReturnProblemDetail_getInfo_EmptyRequest() throws Exception {
        ItemDescriptionRequest request = new ItemDescriptionRequest();

        when(promptService.getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), nullable(Principal.class))).thenReturn(response);

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

        when(promptService.getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), nullable(Principal.class))).thenReturn(response);

        mockMvc.perform(post("/api/tags/clothes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    ItemDescriptionResponse resultResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ItemDescriptionResponse.class);
                    assertEquals("Test Item", resultResponse.getTitle());
                    assertEquals("This is a test item description.", resultResponse.getDescription());
                    assertEquals(2, resultResponse.getTags().length);
                });
        verify(promptService, times(1)).getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), nullable(Principal.class));
    }

    @Test
    void shouldReturnResponseAndSaveHistory_getClothesInfo_AllInfo() throws Exception {
        ClothesRequest request = new ClothesRequest();
        request.setItem("Test Item");
        request.setPlatform("Allegro");
        request.setResponseStyle("formal");
        request.setTargetAudience("young adults");
        request.setTagsQuantity(10);
        request.setColor("Red");
        request.setMaterial("Cotton");
        request.setSize("M");

        when(promptService.getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), any(Principal.class))).thenReturn(response);

        mockMvc.perform(post("/api/tags/clothes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .principal(() -> "testUser"))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ItemDescriptionResponse resultResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ItemDescriptionResponse.class);
                    assertEquals("Test Item", resultResponse.getTitle());
                    assertEquals("This is a test item description.", resultResponse.getDescription());
                    assertEquals(2, resultResponse.getTags().length);
                });
        verify(promptService, times(1)).getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), any(Principal.class));
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

        when(promptService.getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), nullable(Principal.class))).thenThrow(new RuntimeException("Something wrong happened"));

        mockMvc.perform(post("/api/tags/clothes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> {
                    ProblemDetail problemDetail = objectMapper.readValue(result.getResponse().getContentAsString(), ProblemDetail.class);
                    assertNotNull(problemDetail);
                    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), problemDetail.getStatus());
                });
        verify(promptService, times(1)).getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), nullable(Principal.class));
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

        when(promptService.getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), nullable(Principal.class))).thenReturn(response);

        mockMvc.perform(post("/api/tags/electronics")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    ItemDescriptionResponse resultResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ItemDescriptionResponse.class);
                    assertEquals("Test Item", resultResponse.getTitle());
                    assertEquals("This is a test item description.", resultResponse.getDescription());
                    assertEquals(2, resultResponse.getTags().length);
                });
        verify(promptService, times(1)).getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), nullable(Principal.class));
    }

    @Test
    void shouldReturnResponseAndSaveHistory_getElectronicsInfo_AllInfo() throws Exception {
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

        when(promptService.getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), any(Principal.class))).thenReturn(response);

        mockMvc.perform(post("/api/tags/electronics")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .principal(() -> "testUser"))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    ItemDescriptionResponse resultResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ItemDescriptionResponse.class);
                    assertEquals("Test Item", resultResponse.getTitle());
                    assertEquals("This is a test item description.", resultResponse.getDescription());
                    assertEquals(2, resultResponse.getTags().length);
                });
        verify(promptService, times(1)).getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), any(Principal.class));
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

        when(promptService.getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), nullable(Principal.class))).thenThrow(new AiParsingException("Parsing error"));

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
                });
        verify(promptService, times(1)).getResponseAndSaveHistoryIfUserPresent(any(ItemDescriptionRequest.class), nullable(Principal.class));
    }

    @Test
    void shouldReturnHistory() throws Exception {
        Page<ItemDescription> pageBeforeMapping  = new PageImpl<>(List.of(new ItemDescription()));
        when(itemsHistoryService.getHistory(anyString(), any(Pageable.class))).thenReturn(pageBeforeMapping);
        when(itemDescriptionMapper.toResponse(any(ItemDescription.class))).thenReturn(response);

        mockMvc.perform(get("/api/tags/history")
                        .param("page", "1")
                        .param("size", "10")
                        .principal(() -> "testUser"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    ItemDescriptionResponse[] resultResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ItemDescriptionResponse[].class);
                    assertEquals(1, resultResponse.length);
                    assertEquals("Test Item", resultResponse[0].getTitle());
                    assertEquals("This is a test item description.", resultResponse[0].getDescription());
                    assertEquals(2, resultResponse[0].getTags().length);
                });
        verify(itemsHistoryService, times(1)).getHistory(anyString(), any(Pageable.class));
    }

    @Test
    void shouldNotReturnHistory_UserNotFound() throws Exception {
        when(itemsHistoryService.getHistory(anyString(), any(Pageable.class))).thenThrow(new NoSuchElementException("User not found"));

        mockMvc.perform(get("/api/tags/history")
                        .param("page", "1")
                        .param("size", "10")
                        .principal(() -> "testUser"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> {
                    ProblemDetail detail = objectMapper.readValue(result.getResponse().getContentAsString(), ProblemDetail.class);
                    assertFalse(detail.getTitle().isBlank());
                    assertFalse(detail.getDetail().isBlank());
                });
        verify(itemsHistoryService, times(1)).getHistory(anyString(), any(Pageable.class));
    }
}
