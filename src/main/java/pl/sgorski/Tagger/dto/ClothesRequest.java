package pl.sgorski.Tagger.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ClothesRequest extends PromptRequest {

    private String color;
    private String size;
    private String material;
}