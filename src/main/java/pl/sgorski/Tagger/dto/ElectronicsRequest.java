package pl.sgorski.Tagger.dto;

import lombok.Data;

@Data
public class ElectronicsRequest extends PromptRequest{

    private String brand;
    private String model;
    private String specifications;
    private String color;
    private Integer monthsOfWarranty;
}
