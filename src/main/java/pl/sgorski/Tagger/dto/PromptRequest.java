package pl.sgorski.Tagger.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PromptRequest {

    @NotBlank(message = "Style must be specified")
    private String responseStyle;

    @NotBlank(message = "Target audience must be specified")
    private String targetAudience;

    @NotBlank(message = "Item name cannot be blank")
    private String item;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 50, message = "Quantity cannot exceed 50")
    private int tagsQuantity = 10;

    private String platform;
}
