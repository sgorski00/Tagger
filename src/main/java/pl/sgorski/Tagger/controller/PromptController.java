package pl.sgorski.Tagger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.service.ClothesService;
import pl.sgorski.Tagger.service.ElectronicsService;
import pl.sgorski.Tagger.service.ItemsServiceImpl;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Tag(name = "Tags creator", description = "Controller for generating tags, titles and descriptions for products.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved tags, title and description",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "400", description = "Invalid format.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "409", description = "Validation error details.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
})
public class PromptController {

    private final ClothesService clothingService;
    private final ElectronicsService electronicsService;
    private final ItemsServiceImpl itemsService;

    @PostMapping
    @Operation(summary = "Get tags, title and description for every product. Check other endpoints for specific products that are more detailed.")
    public ResponseEntity<?> getInfo(
            @RequestBody @Valid PromptRequest request
    ) {
        return ResponseEntity.ok(itemsService.getFullInfo(request));
    }

    @PostMapping("/clothes")
    @Operation(summary = "Get tags, title and description for clothes")
    public ResponseEntity<?> getClothesInfo(
            @RequestBody @Valid ClothesRequest request
    ) {
        return ResponseEntity.ok(clothingService.getFullInfo(request));
    }

    @PostMapping("/electronics")
    @Operation(summary = "Get tags, title and description for electronics")
    public ResponseEntity<?> getElectronicsInfo(
            @RequestBody @Valid ElectronicsRequest request
    ) {
        return ResponseEntity.ok(electronicsService.getFullInfo(request));
    }
}
