import {Component, input} from '@angular/core';
import {DatePipe} from "@angular/common";
import {RouterLink} from "@angular/router";
import {GenerationResponse} from "../../../generation/generation-response";

@Component({
  selector: 'app-history-item-card',
  imports: [
    DatePipe,
    RouterLink
  ],
  template: `
    <a class="history-item" [routerLink]="['/user/history', item().id]">
      <span class="title">{{ item().title }}</span>
      <span class="date">{{ item().createdAt | date: 'yyyy-MM-dd HH:mm' }}</span>
    </a>
  `,
  styleUrl: './history-item-card.scss',
})
export class HistoryItemCard {
  readonly item = input.required<GenerationResponse>();
}
