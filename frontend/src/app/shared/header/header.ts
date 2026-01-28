import {Component, signal} from '@angular/core';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {
  protected readonly title: string = 'Tagger -  powered by AI'
  protected readonly isMenuOpen = signal<boolean>(false);

  protected toggleMenu() {
    this.isMenuOpen.update(status => !status);
  }
}
