import {Injectable, signal} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoadingService {
  readonly #navigationLoading = signal(false);
  readonly isNavigationLoading = this.#navigationLoading.asReadonly();
  readonly #formLoading = signal(false);
  readonly isFormLoading = this.#formLoading.asReadonly();

  showFormLoading(): void {
    this.#formLoading.set(true);
  }

  hideFormLoading(): void {
    this.#formLoading.set(false);
  }

  showNavigationLoading(): void {
    this.#navigationLoading.set(true);
  }

  hideNavigationLoading(): void {
    this.#navigationLoading.set(false);
  }
}
