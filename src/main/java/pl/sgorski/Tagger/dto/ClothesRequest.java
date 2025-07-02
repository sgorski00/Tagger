package pl.sgorski.Tagger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ClothesRequest extends PromptRequest {

    @Schema(description = "Color of the clothes", examples = {"red", "blue", "green", "black", "white"})
    private String color;
    @Schema(description = "Size of the clothes", examples = {"34", "M", "L", "42", "XXL"})
    private String size;
    @Schema(description = "Material of the clothes", examples = {"100% cotton", "polyester", "50% wool, 40% cotton, 10% polyester", "leather", "silk"})
    private String material;
}