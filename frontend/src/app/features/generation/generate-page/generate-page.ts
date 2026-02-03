import {Component, effect, inject, signal} from '@angular/core';
import {DynamicForm} from "../dynamic-form/dynamic-form";
import {GenerationHttpClient} from "../generation-http-client";
import {GenerationResponse} from "../generation-response";
import {GenerationResult} from "../generation-result/generation-result";
import {ViewportScroller} from '@angular/common';
import {ErrorService, LoadingService} from '../../../core/services';
import {GenerationRequest} from "../generation-request.types";
import {BasePage} from "../../../shared/base-page/base-page";

@Component({
  selector: 'app-generate-page',
    imports: [DynamicForm, GenerationResult, BasePage],
  templateUrl: './generate-page.html',
  styleUrl: './generate-page.scss',
})
export class GeneratePage {
  #apiClient = inject(GenerationHttpClient)
  #scroller = inject(ViewportScroller)
  #errorService = inject(ErrorService)
  protected readonly loading = inject(LoadingService).isFormLoading;
  protected readonly response = signal<GenerationResponse | null>(null);
  protected readonly error = this.#errorService.error;
  protected readonly headerLabel = "Generate your item's info!"

  constructor() {
    effect(() => {
      if(this.loading()) {
        this.#scroller.scrollToAnchor('result', {behavior: 'smooth'});
      }
    });
  }

  protected onGenerate(data: GenerationRequest) {
    this.response.set(null)
    this.#errorService.clearError();
    this.#apiClient.getGenerationResponse(data).subscribe({
      next: (r) => {
        this.response.set(r);
    },
    error: (err) => {
      this.#errorService.setError(err.message || 'An error occurred while generating the response');
    }});
  }

  protected scrollToForm() {
    this.#scroller.scrollToAnchor('form', {behavior: 'smooth'});
  }
}
