import {Injectable, signal} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class MenuStateService {
  readonly #isMenuOpen = signal(false);
  public readonly isMenuOpen = this.#isMenuOpen.asReadonly();

  public toggleMenu(): void {
    this.#isMenuOpen.update(open => !open);
  }
}
