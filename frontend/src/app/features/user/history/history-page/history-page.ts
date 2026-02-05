import {Component, effect, inject, signal} from '@angular/core';
import {BasePage} from "../../../../shared/base-page/base-page";
import {HistoryItemsList} from "../history-items-list/history-items-list";
import {HistoryHttpClient} from "../history-http-client";
import {GenerationResponse} from "../../../generation/generation-response";
import {PageResponse} from "../../../../core/models/page-response";
import {HistoryPagination} from '../history-pagination/history-pagination';

@Component({
  selector: 'app-history-page',
  imports: [
    BasePage,
    HistoryItemsList,
    HistoryPagination
  ],
  templateUrl: './history-page.html',
  styleUrl: './history-page.scss',
})
export class HistoryPage {
  readonly #historyHttpClient = inject(HistoryHttpClient);
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
  protected readonly historyPage = signal(this.#initialHistoryPage);

  constructor() {
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

  protected changePage($event: number) {
    this.page.set($event);
  }
}
