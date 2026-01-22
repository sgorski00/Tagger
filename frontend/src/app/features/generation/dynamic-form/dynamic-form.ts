import {Component, signal} from '@angular/core';
import {TitleCasePipe} from "@angular/common";
import {GeneralForm} from "./general-form/general-form";

@Component({
  selector: 'app-dynamic-form',
  imports: [
    TitleCasePipe,
    GeneralForm
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
