import {Component, input} from '@angular/core';
import {GenerationResponse} from "../generation-response";

@Component({
  selector: 'app-generation-result',
  imports: [],
  templateUrl: './generation-result.html',
  styleUrl: './generation-result.scss',
})
export class GenerationResult {
  response = input.required<GenerationResponse>();
}
