import {Component, input, output} from '@angular/core';
import {GenerationResponse} from "../generation-response";

@Component({
  selector: 'app-generation-result',
  imports: [],
  templateUrl: './generation-result.html',
  styleUrl: './generation-result.scss',
})
export class GenerationResult {
  readonly response = input.required<GenerationResponse>();
  readonly backToForm = output<void>();

  protected getPrettyTags(): string {
    let tagsString = ''
    this.response().tags?.forEach(tag => {
      if (!tag.startsWith('#')) tag = `#${tag}`;
      tagsString += `${tag} `
    })
    return tagsString.trim();
  }

  protected onClick() {
    this.backToForm.emit();
  }
}
