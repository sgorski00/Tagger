import {Component, inject} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {environment} from "../../../../environments/environment";
import {toSignal} from "@angular/core/rxjs-interop";
import {map} from "rxjs";

@Component({
    selector: 'app-unauthorized',
    imports: [],
    templateUrl: './unauthorized-page.html',
    styleUrl: '../error-styles.scss',
})
export class UnauthorizedPage {
    readonly #router = inject(Router);
    readonly #activatedRoute = inject(ActivatedRoute);
    readonly #backendGoogleOauthUrl: string = `${environment.backendUrl}/oauth2/authorization/google`;
    protected readonly details = toSignal(
        this.#activatedRoute.queryParamMap.pipe(
            map(params => params.get('details'))
        ),
        {initialValue: null}
    )

    protected login() {
        this.#router.navigate([this.#backendGoogleOauthUrl])
    }

    protected goHome() {
        this.#router.navigate(['/'])
    }
}
