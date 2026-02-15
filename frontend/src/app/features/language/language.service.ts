import {effect, inject, Injectable, LOCALE_ID, signal} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  readonly #languageKey = 'language';
  readonly #defaultLanguage = inject(LOCALE_ID);
  readonly #selectedLanguage = signal<string>(
    localStorage.getItem(this.#languageKey) ?? this.#defaultLanguage
  );

  constructor() {
    effect(() => {
      localStorage.setItem(this.#languageKey, this.#selectedLanguage());
    });
  }

  saveLanguage(language: string): void {
    this.#selectedLanguage.set(language);
  }

  getLanguage(): string {
    return this.#selectedLanguage();
  }

  removeLanguage(): void {
    localStorage.removeItem(this.#languageKey);
    this.#selectedLanguage.set(this.#defaultLanguage);
  }
}
