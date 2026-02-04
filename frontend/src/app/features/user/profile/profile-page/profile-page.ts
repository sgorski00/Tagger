import {Component, inject, signal} from '@angular/core';
import {RouterLink} from "@angular/router";
import {BasePage} from "../../../../shared/base-page/base-page";
import {HistoryHttpClient} from "../../history/history-http-client";
import {toSignal} from "@angular/core/rxjs-interop";
import {HistoryItemCard} from "../../history/history-item-card/history-item-card";
import {ProfileRow} from "../profile-row/profile-row";
import {map} from "rxjs";

@Component({
    selector: 'app-user-page',
    imports: [
        BasePage,
        RouterLink,
        HistoryItemCard,
        ProfileRow
    ],
    templateUrl: './profile-page.html',
    styleUrl: './profile-page.scss',
})
export class ProfilePage {
    readonly #historyHttpService = inject(HistoryHttpClient);
    protected readonly headerLabel = 'Profile';
    protected readonly historyItems = toSignal(
        this.#historyHttpService.getRequestsHistory().pipe(
            map(resp => resp.content)
        ),
        {initialValue: []}
    )

    protected readonly accountData = signal({
        name: 'John Doe',
        email: 'john.doe@example.com',
        createdAt: new Date('2024-01-15'),
    });
}
