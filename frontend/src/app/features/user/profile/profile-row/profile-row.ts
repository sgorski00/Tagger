import {Component, input} from '@angular/core';
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-profile-row',
  imports: [
    DatePipe
  ],
  template: `
    <div class="row">
      <span class="label">{{ label() }}:</span>
      @if (isDate()) {
        <span class="value">{{ value() | date:'yyyy-MM-dd' }}</span>
      } @else {
        <span class="value">{{ value() }}</span>
      }
    </div>
  `,
  styleUrl: './profile-row.scss',
})
export class ProfileRow {
  readonly label = input.required<string>();
  readonly value = input.required<string | number | Date | null>();

  protected isDate(): boolean {
    return this.value() instanceof Date;
  }
}
