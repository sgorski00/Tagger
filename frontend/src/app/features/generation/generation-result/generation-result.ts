import {Component, inject, input, output} from '@angular/core';
import {GenerationResponse} from "../generation-response";
import {SingleResult} from "./single-result/single-result";
import {TextUtils} from "../../../core/utils/text-utils";

@Component({
  selector: 'app-generation-result',
  imports: [
    SingleResult
  ],
  templateUrl: './generation-result.html',
  styleUrl: './generation-result.scss',
})
export class GenerationResult {
  readonly #textUtils = inject(TextUtils)
  readonly response = input.required<GenerationResponse>();
  readonly backToForm = output<void>();

  protected getPrettyTags(): string {
    return this.#textUtils.getPrettyTags(this.response().tags)
  }

  protected onClick() {
    this.backToForm.emit();
  }
}
