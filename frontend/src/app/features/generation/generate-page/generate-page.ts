import {afterNextRender, Component, effect, inject, signal} from '@angular/core';
import {DynamicForm} from "../dynamic-form/dynamic-form";
import {GenerationHttpClient} from "../generation-http-client";
import {GenerationResponse} from "../generation-response";
import {GeneralGenerationRequest} from "../general-generation-request";
import {ElectronicsGenerationsRequest} from "../electronics-generations-request";
import {ClothesGenerationsRequest} from "../clothes-generations-request";
import {GenerationResult} from "../generation-result/generation-result";
import {ViewportScroller} from '@angular/common';
import {delay} from 'rxjs';

@Component({
  selector: 'app-generate-page',
  imports: [DynamicForm, GenerationResult],
  templateUrl: './generate-page.html',
  styleUrl: './generate-page.scss',
})
export class GeneratePage {
  #apiClient = inject(GenerationHttpClient)
  #scroller = inject(ViewportScroller)
  protected readonly response = signal<GenerationResponse | null>(null);
  protected readonly resultLoading = signal(false)
  protected readonly headerLabel = "Generate your item's info!"

  constructor() {
    effect(() => {
      if(this.resultLoading()) {
        this.#scroller.scrollToAnchor('result', {behavior: 'smooth'});
      }
    });
  }

  protected onGenerate(data: GeneralGenerationRequest | ElectronicsGenerationsRequest | ClothesGenerationsRequest) {
    this.response.set(null)
    this.resultLoading.set(true);

    this.#apiClient.getGenerationResponse(data).subscribe({
      next: (r) => {
        console.log("Generation response received:", r);
        this.response.set(r);
    },
    error: (err) => {
      console.error("Error while generating response:", err);
    },
    complete: () => {
        this.resultLoading.set(false);
    }});
  }
}
