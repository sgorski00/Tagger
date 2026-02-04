import {Component, inject} from '@angular/core';
import {BasePage} from "../../../../shared/base-page/base-page";
import {HistoryItemsList} from "../history-items-list/history-items-list";
import {HistoryHttpClient} from "../history-http-client";
import {toSignal} from "@angular/core/rxjs-interop";
import {GenerationResponse} from "../../../generation/generation-response";
import {PageResponse} from "../../../../core/models/page-response";

@Component({
  selector: 'app-history-page',
  imports: [
    BasePage,
    HistoryItemsList
  ],
  templateUrl: './history-page.html',
  styleUrl: './history-page.scss',
})
export class HistoryPage {
  protected readonly headerLabel = 'History';
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
  protected readonly historyPage = toSignal(
      this.#historyHttpClient.getRequestsHistory(),
      {initialValue: this.#initialHistoryPage}
  );

  getHistoryItems(): ReadonlyArray<GenerationResponse> {
    return this.historyPage().content;
  }
}
