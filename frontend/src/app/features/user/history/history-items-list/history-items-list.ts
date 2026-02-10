import {Component, input, output} from '@angular/core';
import {HistoryItemCard} from "../history-item-card/history-item-card";
import {GenerationResponse} from "../../../generation/generation-response";

@Component({
  selector: 'app-history-items-list',
  imports: [
    HistoryItemCard
  ],
  templateUrl: './history-items-list.html',
  styleUrl: './history-items-list.scss',
})
export class HistoryItemsList {
  readonly items = input.required<ReadonlyArray<GenerationResponse>>();
  readonly titleLabel = input.required<string>();
  readonly showDetails = output<number>()

  protected showItemDetails(id: number) {
    this.showDetails.emit(id);
  }
}
