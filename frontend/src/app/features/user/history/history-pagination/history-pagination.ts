import {Component, input, output} from '@angular/core';

@Component({
  selector: 'app-history-pagination',
  imports: [],
  templateUrl: './history-pagination.html',
  styleUrl: './history-pagination.scss',
})
export class HistoryPagination {
  protected readonly offset: number = 2;
  readonly activePage = input.required<number>()
  readonly totalPages = input.required<number>()
  readonly pageChange = output<number>()

  protected getRange(): ReadonlySet<number> {
    const total = this.totalPages();
    const active = this.activePage();
    const windowSize = 2 * this.offset + 1;

    if (total <= windowSize) {
      return new Set(Array.from({ length: total }, (_, i) => i + 1));
    }

    let start = active - this.offset;
    let end = active + this.offset;

    if (start < 1) {
      start = 1;
      end = windowSize;
    }

    if (end > total) {
      end = total;
      start = total - windowSize + 1;
    }

    return new Set(
      Array.from({ length: end - start + 1 }, (_, i) => start + i)
    );
  }

  protected selectPage(page: number) {
    if(this.activePage() === page) return;
    this.pageChange.emit(page);
  }
}
