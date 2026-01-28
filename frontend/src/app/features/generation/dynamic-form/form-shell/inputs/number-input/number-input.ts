import {Component, input} from '@angular/core';
import {ReactiveFormsModule} from "@angular/forms";
import {BaseInput} from "../base-input";

@Component({
  selector: 'app-number-input',
  imports: [
    ReactiveFormsModule
  ],
  template: `
    <div class="form-field" [class.optional]="isOptional()">
      <label [for]="controlId()">{{ label() }}</label>
      <input [id]="controlId()" [formControl]="control()" [min]="min()" [max]="max()"
             type="number" class="input" [placeholder]="placeholder()"
      />
      @if (isError) {<small class="warning" [textContent]="getErrorMessage()"></small>}
    </div>
  `,
  styleUrl: '../inputs.scss',
})
export class NumberInput extends BaseInput{
  readonly min = input.required<number>();
  readonly max = input.required<number>();

  protected getErrorMessage() {
    if (this.control().errors?.['min']) return `${this.label()} should be at least ${this.min()} characters long`;
    if (this.control().errors?.['max']) return `${this.label()} should be at most ${this.max()} characters long`;
    return '';
  }
}
