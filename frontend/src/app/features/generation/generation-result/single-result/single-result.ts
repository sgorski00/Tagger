import {Component, input, signal, inject} from '@angular/core';
import {Clipboard} from '@angular/cdk/clipboard';

@Component({
  selector: 'app-single-result',
  imports: [],
  templateUrl: './single-result.html',
  styleUrl: './single-result.scss',
})
export class SingleResult {
  readonly title = input.required<string>();
  readonly value = input.required<string>();
  readonly #clipboard = inject(Clipboard);
  protected readonly copySuccess = signal<boolean>(false);

  protected copyToClipboard(): void {
    const success = this.#clipboard.copy(this.value());
    if (success) {
      this.copySuccess.set(true);
      setTimeout(() => this.copySuccess.set(false), 2000);
    }
  }
}
