package pl.sgorski.Tagger.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sgorski.Tagger.dto.ClothesRequest;
import pl.sgorski.Tagger.dto.ElectronicsRequest;
import pl.sgorski.Tagger.dto.PromptRequest;
import pl.sgorski.Tagger.service.ClothesService;
import pl.sgorski.Tagger.service.ElectronicsService;
import pl.sgorski.Tagger.service.ItemsServiceImpl;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class PromptController {

    private final ClothesService clothingService;
    private final ElectronicsService electronicsService;
    private final ItemsServiceImpl itemsService;

    @GetMapping
    public ResponseEntity<?> getInfo(
            @RequestBody @Valid PromptRequest request
    ) {
        return ResponseEntity.ok(itemsService.getFullInfo(request));
    }

    @GetMapping("/clothes")
    public ResponseEntity<?> getClothesInfo(
            @RequestBody @Valid ClothesRequest request
    ) {
        return ResponseEntity.ok(clothingService.getFullInfo(request));
    }

    @GetMapping("/electronics")
    public ResponseEntity<?> getElectronicsInfo(
            @RequestBody @Valid ElectronicsRequest request
    ) {
        return ResponseEntity.ok(electronicsService.getFullInfo(request));
    }
}
