package pl.sgorski.Tagger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PromptResponse {

    @Schema(description = "Generated listing title", example = "Stylish Summer Dress")
    private String title;

    @Schema(description = "Product description", example = "A beautiful and comfortable summer dress perfect for any occasion.")
    private String description;

    @Schema(description = "Generated tags for the product. Every tag starts with '#'.", example = "[\"#summer\", \"#dress\", \"#stylish\", \"#comfortable\"]")
    private String[] tags;

}
