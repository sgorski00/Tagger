import {Component, inject, signal} from '@angular/core';
import {environment} from "../../../environments/environment";
import {AuthService} from "../../features/auth/auth.service";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-header',
  imports: [
    RouterLink
  ],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header {
  readonly #authService = inject(AuthService);
  protected readonly title: string = 'Tagger -  powered by AI'
  protected readonly backendGoogleOauthUrl: string = `${environment.backendUrl}/oauth2/authorization/google`;
  protected readonly isMenuOpen = signal<boolean>(false);

  protected toggleMenu() {
    this.isMenuOpen.update(status => !status);
  }

  protected isAuthenticated(): boolean {
    return this.#authService.isAuthenticated();
  }
}
