import {Component, input, output, signal} from '@angular/core';
import {TitleCasePipe} from "@angular/common";
import {FormShell} from "./form-shell/form-shell";
import {FORM_MODES, FormMode} from "./form-mode";
import {GeneralGenerationRequest} from "../general-generation-request";
import {ElectronicsGenerationsRequest} from "../electronics-generations-request";
import {ClothesGenerationsRequest} from "../clothes-generations-request";

@Component({
  selector: 'app-dynamic-form',
  imports: [
    TitleCasePipe,
    FormShell
  ],
  templateUrl: './dynamic-form.html',
  styleUrl: './dynamic-form.scss',
})
export class DynamicForm {
  readonly loading = input.required<boolean>();
  protected readonly formSubmit = output<GeneralGenerationRequest | ElectronicsGenerationsRequest | ClothesGenerationsRequest>();
  protected readonly mode = signal<FormMode>('general');
  protected readonly tabs: readonly FormMode[] = FORM_MODES;

  protected setMode(mode: FormMode) {
    this.mode.set(mode);
  }

  protected onSubmit(data: GeneralGenerationRequest | ElectronicsGenerationsRequest | ClothesGenerationsRequest) {
    this.formSubmit.emit(data);
  }
}
