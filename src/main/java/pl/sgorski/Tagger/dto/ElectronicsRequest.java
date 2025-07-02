package pl.sgorski.Tagger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ElectronicsRequest extends PromptRequest{

    @Schema(description = "Brand of the electronic item", examples = {"Apple", "Samsung", "Sony", "LG", "Dell"})
    private String brand;
    @Schema(description = "Model of the electronic item", examples = {"iPhone 16e", "Galaxy S23", "Xperia 5 IV", "OLED55CX3LA", "Inspiron 15"})
    private String model;
    @Schema(description = "Specifications of the electronic item", examples = {"128GB storage, 6GB RAM", "4K resolution, OLED display", "Intel i7, 16GB RAM, 512GB SSD"})
    private String specifications;
    @Schema(description = "Color of the electronic item", examples = {"black", "white", "silver", "gold", "blue"})
    private String color;
    @Schema(description = "Warranty period in months", example = "24")
    private Integer monthsOfWarranty;
}
