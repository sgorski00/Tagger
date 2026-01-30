import {Component, effect, inject} from '@angular/core';
import {NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router, RouterOutlet} from '@angular/router';
import {Header} from './shared/header/header';
import {Footer} from './shared/footer/footer';
import {LoadingService} from "./core/services";
import {toSignal} from "@angular/core/rxjs-interop";
import {filter} from "rxjs";

@Component({
    selector: 'app-root',
    imports: [RouterOutlet, Header, Footer],
    templateUrl: './app.html',
    styleUrl: './app.scss'
})
export class App {
    readonly #loadingService = inject(LoadingService);
    readonly #router = inject(Router);
    protected readonly isNavigationLoading = this.#loadingService.isNavigationLoading;

    readonly #navigationEvent = toSignal(
        this.#router.events.pipe(
            filter(e =>
                e instanceof NavigationStart ||
                e instanceof NavigationEnd ||
                e instanceof NavigationCancel ||
                e instanceof NavigationError
            )
        )
    );

    constructor() {
        effect(() => {
            const event = this.#navigationEvent();
            if (event instanceof NavigationStart) {
                this.#loadingService.showNavigationLoading();
            } else {
                this.#loadingService.hideNavigationLoading();
            }
        });
    }
}
