import {Component, effect, inject, input, signal} from '@angular/core';
import {HistoryHttpClient} from "../history-http-client";
import {GenerationResponse} from "../../../generation/generation-response";
import {TextUtils} from "../../../../core/utils/text-utils";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-history-item-full-card',
    imports: [
        DatePipe
    ],
  templateUrl: './history-item-full-card.html',
  styleUrl: './history-item-full-card.scss',
})
export class HistoryItemFullCard {
  readonly #historyHttpClient = inject(HistoryHttpClient);
  readonly #textUtils = inject(TextUtils)
  readonly itemId = input.required<number | null>();
  protected readonly item = signal<GenerationResponse | null>(null);

  constructor() {
    effect(() => {
        const itemId = this.itemId();
        if (itemId) {
          this.#historyHttpClient.getRequestDetails(itemId)
            .subscribe(data => this.item.set(data));
        }
    })
  }

  protected getPrettyTags(): string {
      const tags = this.item()?.tags;
      if(!tags || tags?.length === 0) return '';
      return this.#textUtils.getPrettyTags(tags);
  }
}
