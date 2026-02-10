import {Component, effect, inject, signal} from '@angular/core';
import {BasePage} from "../../../../shared/base-page/base-page";
import {HistoryItemsList} from "../history-items-list/history-items-list";
import {HistoryHttpClient} from "../history-http-client";
import {GenerationResponse} from "../../../generation/generation-response";
import {PageResponse} from "../../../../core/models/page-response";
import {HistoryPagination} from '../history-pagination/history-pagination';
import {HistoryItemFullCard} from "../history-item-full-card/history-item-full-card";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-history-page',
    imports: [
        BasePage,
        HistoryItemsList,
        HistoryPagination,
        HistoryItemFullCard
    ],
    templateUrl: './history-page.html',
    styleUrl: './history-page.scss',
})
export class HistoryPage {
    readonly #historyHttpClient = inject(HistoryHttpClient);
    readonly #activeRoute = inject(ActivatedRoute);
    readonly #initialHistoryPage: PageResponse<GenerationResponse> = {
        content: [],
        pageable: {
            pageNumber: 0,
            pageSize: 0,
        },
        totalElements: 0,
        totalPages: 0,
        last: false,
        first: true
    }

    protected readonly page = signal(1);
    protected readonly itemId = signal<number | null>(null);
    protected readonly historyPage = signal(this.#initialHistoryPage)

    constructor() {
        const idFromRoute = Number(this.#activeRoute.snapshot.paramMap.get('id'));
        if (idFromRoute)  this.itemId.set(idFromRoute);

        effect(() => {
            const page = this.page();
            this.#historyHttpClient.getRequestsHistory(page)
                .subscribe(data => this.historyPage.set(data));
        })
    }

    protected getHistoryItems(): ReadonlyArray<GenerationResponse> {
        return this.historyPage().content;
    }

    protected getActivePage(): number {
        return this.historyPage().pageable.pageNumber + 1;
    }

    protected getTotalPages(): number {
        return this.historyPage().totalPages;
    }

    protected onPageChange($event: number) {
        this.page.set($event);
    }

    protected onItemChange($event: number) {
        this.itemId.set($event);
    }
}
