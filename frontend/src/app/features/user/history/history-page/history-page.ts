import { Component } from '@angular/core';
import {BasePage} from "../../../../shared/base-page/base-page";

@Component({
  selector: 'app-history-page',
    imports: [
        BasePage
    ],
  templateUrl: './history-page.html',
  styleUrl: './history-page.scss',
})
export class HistoryPage {
  readonly headerLabel = 'History';
}
