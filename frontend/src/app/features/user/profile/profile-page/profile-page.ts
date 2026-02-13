import {Component, inject} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {BasePage} from "../../../../shared/base-page/base-page";
import {HistoryHttpClient} from "../../history/history-http-client";
import {toSignal} from "@angular/core/rxjs-interop";
import {ProfileRow} from "../profile-row/profile-row";
import {map} from "rxjs";
import {HistoryItemsList} from "../../history/history-items-list/history-items-list";
import {ProfileHttpClient} from "../profile-http-client";

@Component({
    selector: 'app-user-profile-page',
    imports: [
        BasePage,
        RouterLink,
        ProfileRow,
        HistoryItemsList
    ],
    templateUrl: './profile-page.html',
    styleUrl: './profile-page.scss',
})
export class ProfilePage {
    readonly #profileHttpService = inject(ProfileHttpClient);
    readonly #historyHttpService = inject(HistoryHttpClient);
    readonly #router = inject(Router);
    protected readonly headerLabel = 'Profile';
    protected readonly historyItems = toSignal(
        this.#historyHttpService.getRequestsHistory().pipe(
            map(resp => resp.content)
        ),
        {initialValue: []}
    )
    protected readonly accountData = toSignal(
        this.#profileHttpService.getProfile(),
        {initialValue: null}
    );

    protected onItemClick(id: number) {
        this.#router.navigate(['user', 'history', id]);
    }
}
