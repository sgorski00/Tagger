import {booleanAttribute, Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-text-input',
  imports: [
    ReactiveFormsModule
  ],
  template: `
    <div class="form-field" [class.optional]="isOptional">
      <label [for]="controlId">{{ label }}</label>
      <input [id]="controlId" [formControl]="control" type="text" class="input" [placeholder]="placeholder"/>
      @if (isError) {<small class="warning" [textContent]="getErrorMessage()"></small>}
    </div>`,
  styleUrl: '../inputs.scss',
})
export class TextInput {
  @Input({required: true}) label!: string;
  @Input({required: true}) control!: FormControl;
  @Input({required: true}) controlId!: string;
  @Input({required: true}) isSubmitted?: boolean;
  @Input({transform: booleanAttribute}) isOptional = false;
  @Input() placeholder = '';
  @Input() minLength?: number;

  protected getErrorMessage() {
    if (this.control.errors?.['required']) return `${this.label} is required`;
    if (this.control.errors?.['minlength']) return `${this.label} should be at least ${this.minLength} characters long`;
    return '';
  }

  protected get isError() {
    return this.control.invalid && (this.control.dirty || this.control.touched || this.isSubmitted);
  }
}
