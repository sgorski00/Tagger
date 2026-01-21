import {Component, inject} from '@angular/core';
import {MenuStateService} from './menu-state/menu-state.service';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {
  protected readonly title: string = 'Tagger -  powered by AI'
  protected readonly menuStateService = inject(MenuStateService)

  protected readonly isMenuOpen = this.menuStateService.isMenuOpen;
  protected toggleMenu() {
    this.menuStateService.toggleMenu();
  }
}
