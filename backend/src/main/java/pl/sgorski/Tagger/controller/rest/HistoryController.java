package pl.sgorski.Tagger.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
import pl.sgorski.Tagger.mapper.ItemDescriptionMapper;
import pl.sgorski.Tagger.service.ItemsHistoryService;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
@Tag(name = "User generation history", description = "Controller for viewing history of generated item details.")
@ApiResponse(responseCode = "500", description = "Internal server error",
  content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
public class HistoryController {

  private final ItemsHistoryService itemsHistoryService;
  private final ItemDescriptionMapper mapper;

  @GetMapping
  @Operation(summary = "Get list of generated tags, titles and descriptions for products.")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved tags, title and description")
  public ResponseEntity<Page<ItemDescriptionResponse>> getHistory(
    @RequestParam(value = "page", defaultValue = "1") int page,
    @RequestParam(value = "size", defaultValue = "10") int size
  ) {
    var pageRequest = PageRequest.of(page - 1, size);
    var result = itemsHistoryService.getHistory(pageRequest)
      .map(mapper::toResponse);
    return ResponseEntity.ok(result);
  }

  @GetMapping("{id}")
  @Operation(summary = "Get details of generated tags, title and description for product by id.")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved tags, title and description")
  public ResponseEntity<ItemDescriptionResponse> getHistoryItem(
    @PathVariable Long id
  ) {
    var item = itemsHistoryService.getHistoryItem(id);
    return ResponseEntity.ok(mapper.toResponse(item));
  }

}
