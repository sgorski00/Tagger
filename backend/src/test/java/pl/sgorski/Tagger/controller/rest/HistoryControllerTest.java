package pl.sgorski.Tagger.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
import pl.sgorski.Tagger.mapper.ItemDescriptionMapper;
import pl.sgorski.Tagger.model.ItemDescription;
import pl.sgorski.Tagger.service.ItemsHistoryService;
import pl.sgorski.Tagger.service.auth.JwtService;
import pl.sgorski.Tagger.service.auth.UserService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HistoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class HistoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ItemsHistoryService itemsHistoryService;

  @MockitoBean
  private ItemDescriptionMapper itemDescriptionMapper;

  @MockitoBean
  private JwtService jwtService;

  @MockitoBean
  private UserService userService;

  private ItemDescriptionResponse response;

  @BeforeEach
  void setUp() {
    response = new ItemDescriptionResponse();
    response.setTitle("Test Item");
    response.setDescription("This is a test item description.");
    response.setTags(new String[]{"#test", "#item"});
  }

  @Test
  void shouldReturnHistory() throws Exception {
    var pageBeforeMapping  = new PageImpl<>(List.of(new ItemDescription()));
    when(itemsHistoryService.getHistory(any(Pageable.class))).thenReturn(pageBeforeMapping);
    when(itemDescriptionMapper.toResponse(any(ItemDescription.class))).thenReturn(response);

    mockMvc.perform(get("/api/history")
        .param("page", "1")
        .param("size", "10")
        .principal(() -> "testUser"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content").isArray())
      .andExpect(jsonPath("$.content.length()").value(1))
      .andExpect(jsonPath("$.content[0].title").value("Test Item"))
      .andExpect(jsonPath("$.content[0].description").value("This is a test item description."))
      .andExpect(jsonPath("$.content[0].tags.length()").value(2));
    verify(itemsHistoryService, times(1)).getHistory(any(Pageable.class));
  }

  @Test
  void shouldNotReturnHistory_UserNotFound() throws Exception {
    when(itemsHistoryService.getHistory(any(Pageable.class))).thenThrow(new NoSuchElementException("User not found"));

    mockMvc.perform(get("/api/history")
        .param("page", "1")
        .param("size", "10")
        .principal(() -> "testUser"))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.title").isNotEmpty())
      .andExpect(jsonPath("$.detail").isNotEmpty());
    verify(itemsHistoryService, times(1)).getHistory(any(Pageable.class));
  }
}
