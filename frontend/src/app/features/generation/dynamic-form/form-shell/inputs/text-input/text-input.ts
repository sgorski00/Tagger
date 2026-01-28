import {Component, input} from '@angular/core';
import {ReactiveFormsModule} from "@angular/forms";
import {BaseInput} from "../base-input";

@Component({
  selector: 'app-text-input',
  imports: [
    ReactiveFormsModule
  ],
  template: `
    <div class="form-field" [class.optional]="isOptional()">
      <label [for]="controlId()">{{ label() }}</label>
      <input [id]="controlId()" [formControl]="control()" type="text" class="input" [placeholder]="placeholder()"/>
      @if (isError) {<small class="warning" [textContent]="getErrorMessage()"></small>}
    </div>`,
  styleUrl: '../inputs.scss',
})
export class TextInput extends BaseInput{
  readonly minLength = input<number>();

  protected getErrorMessage() {
    if (this.control().errors?.['required']) return `${this.label()} is required`;
    if (this.control().errors?.['minlength']) return `${this.label()} should be at least ${this.minLength()} characters long`;
    return ''
  }
}
