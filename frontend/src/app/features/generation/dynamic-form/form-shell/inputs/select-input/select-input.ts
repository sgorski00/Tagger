import {Component, input} from '@angular/core';
import {ReactiveFormsModule} from "@angular/forms";
import {TitleCasePipe} from "@angular/common";
import {BaseInput} from "../base-input";

@Component({
  selector: 'app-select-input',
  imports: [
    ReactiveFormsModule,
    TitleCasePipe
  ],
  template: `
    <div class="form-field" [class.optional]="isOptional()">
      <label [for]="controlId()">{{ label() }}</label>
      <select [id]="controlId()" [formControl]="control()" class="input">
        <option value="" disabled>{{ placeholder() }}</option>
        @for (option of options(); track option) {
          <option [value]="option">{{ option | titlecase }}</option>
        }
      </select>
      @if (isError) {<small class="warning">{{ label() }} is required</small>}
    </div>
  `,
  styleUrl: '../inputs.scss',
})
export class SelectInput extends BaseInput{
  override readonly placeholder = input<string>('Please select an option');
  readonly options = input.required<ReadonlyArray<string>>();
}
