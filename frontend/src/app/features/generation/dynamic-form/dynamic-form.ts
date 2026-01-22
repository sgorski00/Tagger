import {Component, signal} from '@angular/core';
import {TitleCasePipe} from "@angular/common";

@Component({
  selector: 'app-dynamic-form',
  imports: [
    TitleCasePipe
  ],
  templateUrl: './dynamic-form.html',
  styleUrl: './dynamic-form.scss',
})
export class DynamicForm {
  protected readonly tabs = ['general', 'clothes', 'electronics'];
  protected readonly activeTab = signal(0);

  protected setActiveTab(index: number) {
    this.activeTab.set(index);
  }
}
