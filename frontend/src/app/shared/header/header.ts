import {Component, signal} from '@angular/core';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {
  protected readonly title: string = 'Tagger -  powered by AI'
  private readonly menuOpen = signal(false);

  protected toggleMenu(): void {
    this.menuOpen.update(v => !v);
  }

  protected isMenuOpen(): boolean {
    return this.menuOpen();
  }
}
