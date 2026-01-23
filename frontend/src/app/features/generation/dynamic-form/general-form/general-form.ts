import {Component, signal} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {TitleCasePipe} from "@angular/common";

@Component({
    selector: 'app-general-form',
    imports: [
        ReactiveFormsModule,
        TitleCasePipe
    ],
    templateUrl: './general-form.html',
    styleUrl: '../shared-form.scss',
})
export class GeneralForm {
    protected readonly submitted = signal(false);
    protected readonly tagsQuantityMin = 1;
    protected readonly tagsQuantityMax = 30;
    protected readonly itemNameMinLength = 3;
    protected readonly platforms: Array<string> = ['amazon', 'ebay', 'vinted'];
    protected readonly responseStyles: Array<string> = ['formal', 'casual', 'humorous'];

    readonly #fb = new FormBuilder();
    readonly #initialFormValue = {
        item: '',
        tagsQuantity: 10,
        platform: this.platforms[0],
        targetAudience: '',
        responseStyle: this.responseStyles[0],
    }

    protected readonly form = this.#fb.group({
        item: this.#fb.control<string>(this.#initialFormValue.item, [Validators.required, Validators.minLength(this.itemNameMinLength)]),
        tagsQuantity: this.#fb.control<number | null>(this.#initialFormValue.tagsQuantity, [
            Validators.min(this.tagsQuantityMin),
            Validators.max(this.tagsQuantityMax)
        ]),
        platform: this.#fb.control<string>(this.#initialFormValue.platform),
        targetAudience: this.#fb.control<string>(this.#initialFormValue.targetAudience, Validators.required),
        responseStyle: this.#fb.control<string>(this.#initialFormValue.responseStyle, Validators.required),
    });

    protected onSubmit(): void {
        this.submitted.set(true)
        console.log(this.form.value);
    }

    protected onClear(): void {
        this.submitted.set(false)
        this.form.reset(this.#initialFormValue);
    }
}
