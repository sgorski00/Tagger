package pl.sgorski.Tagger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PromptRequest {

    @NotBlank(message = "{validation.style.notBlank}")
    @Schema(description = "Style of the response", examples = {"casual", "formal", "youthful", "elegant", "modern"})
    private String responseStyle;

    @NotBlank(message = "{validation.target.notBlank}")
    @Schema(description = "Target audience for the product", examples = {"teenagers", "adults", "seniors", "children"})
    private String targetAudience;

    @NotBlank(message = "{validation.item.notBlank}")
    @Schema(description = "Name of the item", examples = {"Smartphone", "T-shirt", "Jeans", "iPhone 16e 128GB", "Toyota Corolla 2023"})
    private String item;

    @Min(value = 1, message = "{validation.tags.min}")
    @Max(value = 50, message = "{validation.tags.max}")
    @Schema(description = "Number of tags to generate", example = "10", defaultValue = "10")
    private int tagsQuantity = 10;

    @Schema(description = "Platform for which the item is intended", examples = {"ebay", "amazon", "vinted"})
    private String platform;
}
