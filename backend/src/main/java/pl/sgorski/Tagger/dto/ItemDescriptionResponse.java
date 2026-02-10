package pl.sgorski.Tagger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDescriptionResponse {

    @Schema(description = "Unique identifier of the item description", example = "1")
    private Long id;

    @Schema(description = "Generated listing title", example = "Stylish Summer Dress")
    private String title;

    @Schema(description = "Product description", example = "A beautiful and comfortable summer dress perfect for any occasion.")
    private String description;

    @Schema(description = "Generated tags for the product. Every tag starts with '#'.", example = "[\"#summer\", \"#dress\", \"#stylish\", \"#comfortable\"]")
    private String[] tags;

    @Schema(description = "Time when the item description was created", example = "2024-06-01T12:00:00")
    private LocalDateTime createdAt;
}
