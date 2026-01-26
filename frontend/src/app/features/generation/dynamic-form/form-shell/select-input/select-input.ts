import {booleanAttribute, Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {TitleCasePipe} from "@angular/common";

@Component({
  selector: 'app-select-input',
  imports: [
    ReactiveFormsModule,
    TitleCasePipe
  ],
  template: `
    <div class="form-field" [class.optional]="isOptional">
      <label [for]="controlId">{{ label }}</label>
      <select [id]="controlId" [formControl]="control" class="input">
        <option value="" disabled>{{ placeholder }}</option>
        @for (option of options; track option) {
          <option [value]="option">{{ option | titlecase }}</option>
        }
      </select>
      @if (isError) {<small class="warning">{{ label }} is required</small>}
    </div>
  `,
  styleUrl: '../inputs.scss',
})
export class SelectInput {
  @Input({required: true}) control!: FormControl
  @Input({required: true}) controlId!: string;
  @Input({required: true}) label!: string;
  @Input({required: true}) options!: string[]
  @Input({required: true}) isSubmitted?: boolean;
  @Input({transform: booleanAttribute}) isOptional = false;
  @Input() placeholder = 'Select an option';

  protected get isError() {
    return this.control.invalid && (this.control.dirty || this.control.touched || this.isSubmitted);
  }
}
