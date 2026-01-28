import {Injectable, signal} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoadingService {
  readonly #loadingCount = signal(0);
  readonly isLoading = this.#loadingCount.asReadonly();

  show(): void {
    this.#loadingCount.update(count => count + 1);
  }

  hide(): void {
    this.#loadingCount.update(count => Math.max(0, count - 1));
  }
}
