import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = 'Tagger - powered by AI';
  private readonly menuOpen = signal(false);

  protected toggleMenu(): void {
    this.menuOpen.update(v => !v);
  }

  protected isMenuOpen(): boolean {
    return this.menuOpen();
  }
}
