package pl.sgorski.Tagger.controller.rest;

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
import pl.sgorski.Tagger.dto.ItemDescriptionRequest;
import pl.sgorski.Tagger.dto.ItemDescriptionResponse;
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

    @PostMapping("/general")
    @Operation(summary = "Get tags, title and description for every product. Check other endpoints for specific products that are more detailed.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tags, title and description"),
            @ApiResponse(responseCode = "201", description = "Successfully retrieved tags, title and description. Response saved to user history."),
            @ApiResponse(responseCode = "400", description = "Invalid format.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Validation error details.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<ItemDescriptionResponse> getInfo(
            @RequestBody @Valid ItemDescriptionRequest request,
            Principal principal
    ) {
        var code = getResponseCode(principal);
        return ResponseEntity.status(code).body(promptService.getResponseAndSaveHistoryIfUserPresent(request));
    }

    @PostMapping("/clothes")
    @Operation(summary = "Get tags, title and description for clothes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tags, title and description"),
            @ApiResponse(responseCode = "201", description = "Successfully retrieved tags, title and description. Response saved to user history."),
            @ApiResponse(responseCode = "400", description = "Invalid format.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Validation error details.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<ItemDescriptionResponse> getClothesInfo(
            @RequestBody @Valid ClothesRequest request,
            Principal principal
    ) {
        var code = getResponseCode(principal);
        return ResponseEntity.status(code).body(promptService.getResponseAndSaveHistoryIfUserPresent(request));
    }

    @PostMapping("/electronics")
    @Operation(summary = "Get tags, title and description for electronics")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved tags, title and description"),
            @ApiResponse(responseCode = "201", description = "Successfully retrieved tags, title and description"),
            @ApiResponse(responseCode = "400", description = "Invalid format.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Validation error details.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<ItemDescriptionResponse> getElectronicsInfo(
            @RequestBody @Valid ElectronicsRequest request,
            Principal principal
    ) {
        var code = getResponseCode(principal);
        return ResponseEntity.status(code).body(promptService.getResponseAndSaveHistoryIfUserPresent(request));
    }

    private int getResponseCode(Principal principal) {
        return principal == null ? 200 : 201;
    }
}
