package pl.sgorski.Tagger.dto;

import lombok.Data;

@Data
public class PromptResponse {

    private String title;
    private String description;
    private String[] tags;

}
