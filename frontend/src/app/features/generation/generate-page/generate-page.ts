import {Component, inject, signal} from '@angular/core';
import {DynamicForm} from "../dynamic-form/dynamic-form";
import {GenerationHttpClient} from "../generation-http-client";
import {GenerationResponse} from "../generation-response";
import {GeneralGenerationRequest} from "../general-generation-request";
import {ElectronicsGenerationsRequest} from "../electronics-generations-request";
import {ClothesGenerationsRequest} from "../clothes-generations-request";
import {GenerationResult} from "../generation-result/generation-result";

@Component({
  selector: 'app-generate-page',
  imports: [DynamicForm, GenerationResult],
  templateUrl: './generate-page.html',
  styleUrl: './generate-page.scss',
})
export class GeneratePage {
  #apiClient = inject(GenerationHttpClient)
  protected readonly response = signal<GenerationResponse | null>(null);
  protected readonly headerLabel = "Generate your item's info!"

  protected onGenerate(data: GeneralGenerationRequest | ElectronicsGenerationsRequest | ClothesGenerationsRequest) {
    this.#apiClient.getGenerationResponse(data).subscribe((r) => {
      console.log("Generation response received:", r);
      this.response.set(r);
    });
  }
}
