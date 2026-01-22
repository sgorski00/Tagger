import {Component} from '@angular/core';
import {DynamicForm} from "../dynamic-form/dynamic-form";

@Component({
  selector: 'app-generate-page',
  imports: [DynamicForm],
  templateUrl: './generate-page.html',
  styleUrl: './generate-page.scss',
})
export class GeneratePage {
  protected readonly headerLabel = "Generate your item's info!"
}
