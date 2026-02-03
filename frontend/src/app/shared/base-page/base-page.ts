import {Component, input} from '@angular/core';

@Component({
  selector: 'app-base-page',
  imports: [],
  templateUrl: './base-page.html',
  styleUrl: './base-page.scss',
})
export class BasePage {
  readonly headerLabel = input.required<string>();
}
