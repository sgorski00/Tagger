import {booleanAttribute, Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-number-input',
  imports: [
    ReactiveFormsModule
  ],
  template: `
    <div class="form-field" [class.optional]="isOptional">
      <label [for]="controlId">{{ label }}</label>
      <input [id]="controlId" [formControl]="control" [min]="min" [max]="max"
             type="number" class="input" [placeholder]="placeholder"
      />
      @if (isError) {<small class="warning" [textContent]="getErrorMessage()"></small>}
    </div>
  `,
  styleUrl: '../inputs.scss',
})
export class NumberInput {
  @Input({required: true}) label!: string;
  @Input({required: true}) control!: FormControl;
  @Input({required: true}) controlId!: string;
  @Input({required: true}) min!: number;
  @Input({required: true}) max!: number;
  @Input({required: true}) isSubmitted?: boolean;
  @Input({transform: booleanAttribute}) isOptional = false;
  @Input() placeholder = '';

  protected getErrorMessage() {
    if (this.control.errors?.['min']) return `${this.label} should be at least ${this.min} characters long`;
    if (this.control.errors?.['max']) return `${this.label} should be at most ${this.max} characters long`;
    return '';
  }

  protected get isError() {
    return this.control.invalid && (this.control.dirty || this.control.touched || this.isSubmitted);
  }
}
