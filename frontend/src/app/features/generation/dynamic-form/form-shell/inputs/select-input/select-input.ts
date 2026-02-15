import {Component, input, output} from '@angular/core';
import {ReactiveFormsModule} from "@angular/forms";
import {TitleCasePipe} from "@angular/common";
import {BaseInput} from "../base-input";
import {Language} from '../../../../../language/language-config';

@Component({
  selector: 'app-select-input',
  imports: [
    ReactiveFormsModule,
    TitleCasePipe
  ],
  template: `
    <div class="form-field" [class.optional]="isOptional()">
      <label [for]="controlId()">{{ label() }}</label>
      <select (change)="onSelectChange($event)" [id]="controlId()" [formControl]="control()" class="input">
        <option value="" disabled>{{ placeholder() }}</option>
        @for (option of options(); track option) {
          @if (isLanguageSelect()) {
            <option [value]="asLanguage(option).code">{{ asLanguage(option).name | titlecase }}</option>
          } @else {
            <option [value]="option">{{ asString(option) | titlecase }}</option>
          }
        }
      </select>
      @if (isError) {<small class="warning">{{ label() }} is required</small>}
    </div>
  `,
  styleUrl: '../inputs.scss',
})
export class SelectInput extends BaseInput{
  override readonly placeholder = input<string>('Please select an option');
  readonly options = input.required<ReadonlySet<string> | ReadonlySet<Language>>();
  readonly isLanguageSelect = input<boolean>(false);
  languageChange = output<string>();

  asLanguage(option: string | Language): Language {
    return option as Language;
  }

  asString(option: string | Language): string {
    return option as string;
  }

  onSelectChange(event: Event) {
    if(this.isLanguageSelect()) {
      const language = (event.target as HTMLSelectElement).value;
      this.languageChange.emit(language);
    }
  }
}
