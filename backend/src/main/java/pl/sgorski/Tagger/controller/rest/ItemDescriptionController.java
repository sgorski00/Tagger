package pl.sgorski.Tagger.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.mapper.ItemDescriptionMapper;
import pl.sgorski.Tagger.service.ItemsHistoryService;
import pl.sgorski.Tagger.service.PromptService;

import java.security.Principal;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Tag(name = "Tags creator", description = "Controller for generating tags, titles and descriptions for products.")
@ApiResponse(responseCode = "500", description = "Internal server error",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
public class ItemDescriptionController {

    private final PromptService promptService;
    private final ItemsHistoryService itemsHistoryService;
    private final ItemDescriptionMapper mapper;

    @PostMapping
    @Operation(summary = "Get tags, title and description for every product. Check other endpoints for specific products that are more detailed.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tags, title and description",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "201", description = "Successfully retrieved tags, title and description. Response saved to user history.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Invalid format.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Validation error details.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<?> getInfo(
            @RequestBody @Valid ItemDescriptionRequest request,
            Principal principal
    ) {
        int code = getResponseCode(principal);
        return ResponseEntity.status(code).body(promptService.getResponseAndSaveHistoryIfUserPresent(request, principal));
    }

    @PostMapping("/clothes")
    @Operation(summary = "Get tags, title and description for clothes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tags, title and description",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "201", description = "Successfully retrieved tags, title and description. Response saved to user history.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Invalid format.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Validation error details.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<?> getClothesInfo(
            @RequestBody @Valid ClothesRequest request,
            Principal principal
    ) {
        int code = getResponseCode(principal);
        return ResponseEntity.status(code).body(promptService.getResponseAndSaveHistoryIfUserPresent(request, principal));
    }

    @PostMapping("/electronics")
    @Operation(summary = "Get tags, title and description for electronics")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tags, title and description",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "201", description = "Successfully retrieved tags, title and description",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Invalid format.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Validation error details.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<?> getElectronicsInfo(
            @RequestBody @Valid ElectronicsRequest request,
            Principal principal
    ) {
        int code = getResponseCode(principal);
        return ResponseEntity.status(code).body(promptService.getResponseAndSaveHistoryIfUserPresent(request, principal));
    }

    @GetMapping("/history")
    @Operation(summary = "Get history of generated tags, titles and descriptions for products.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved tags, title and description",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<?> getHistory(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Principal principal
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        var result = itemsHistoryService.getHistory(principal.getName(), pageRequest).stream()
                .map(mapper::toResponse);
        return ResponseEntity.ok(result);
    }

    private int getResponseCode(Principal principal) {
        return principal == null ? 200 : 201;
    }
}
