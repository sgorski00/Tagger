import {Component, output, signal} from '@angular/core';
import {TitleCasePipe} from "@angular/common";
import {FormShell} from "./form-shell/form-shell";
import {FORM_MODES, FormMode} from "./form-mode";
import {GenerationRequest} from "../generation-request.types";

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
  protected readonly formSubmit = output<GenerationRequest>();
  protected readonly mode = signal<FormMode>('general');
  protected readonly tabs: ReadonlyArray<FormMode> = FORM_MODES;

  protected setMode(mode: FormMode) {
    this.mode.set(mode);
  }

  protected onSubmit(data: GenerationRequest) {
    this.formSubmit.emit(data);
  }
}
