import {Component, signal} from '@angular/core';

@Component({
  selector: 'app-generate-page',
  imports: [],
  templateUrl: './generate-page.component.html',
  styleUrl: './generate-page.component.scss',
})
export class GeneratePageComponent {
  protected readonly bgColor = signal('#3b7')
}
