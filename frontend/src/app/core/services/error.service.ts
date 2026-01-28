import {Injectable, signal} from '@angular/core';

export interface ErrorMessage {
  message: string;
  timestamp: Date;
}

@Injectable({
  providedIn: 'root',
})
export class ErrorService {
  readonly #error = signal<ErrorMessage | null>(null);
  readonly error = this.#error.asReadonly();

  setError(message: string): void {
    this.#error.set({
      message,
      timestamp: new Date(),
    });
  }

  clearError(): void {
    this.#error.set(null);
  }
}
