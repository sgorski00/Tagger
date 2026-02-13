import {Component, inject} from '@angular/core';
import {Router} from "@angular/router";
import {environment} from "../../../../environments/environment";

@Component({
    selector: 'app-unauthorized',
    imports: [],
    templateUrl: './unauthorized-page.html',
    styleUrl: '../error-styles.scss',
})
export class UnauthorizedPage {
    readonly #router = inject(Router);
    readonly #backendGoogleOauthUrl: string = `${environment.backendUrl}/oauth2/authorization/google`;
    protected readonly details: string | null = this.#router.currentNavigation()?.extras.state?.['details']
        ?? history.state?.['details']
        ?? null;

    protected login() {
        this.#router.navigate([this.#backendGoogleOauthUrl])
    }

    protected goHome() {
        this.#router.navigate(['/'])
    }
}
