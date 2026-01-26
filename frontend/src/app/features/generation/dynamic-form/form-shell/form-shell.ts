import {Component, inject, Input, signal, OnChanges, SimpleChanges} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {TitleCasePipe} from "@angular/common";
import {FormMode} from "../form-mode";
import {CustomFormBuilder} from "../custom-form-builder/custom-form-builder.service";
import {TextInput} from "./text-input/text-input";
import {NumberInput} from "./number-input/number-input";
import {SelectInput} from "./select-input/select-input";

@Component({
    selector: 'app-form-shell',
    imports: [
        ReactiveFormsModule,
        TitleCasePipe,
        TextInput,
        NumberInput,
        SelectInput
    ],
    templateUrl: './form-shell.html',
    styleUrl: './form-shell.scss',
})
export class FormShell implements OnChanges{
    @Input({required: true}) mode!: FormMode;
    readonly #customFormBuilder = inject(CustomFormBuilder);
    readonly #fb = new FormBuilder();
    protected form!: FormGroup;
    protected initialFormValue!: any;
    protected formConfig!: any;
    protected readonly submitted = signal(false);

    ngOnChanges(changes: SimpleChanges): void {
        if(changes['mode'] && this.mode) {
            this.form = this.#customFormBuilder.buildForm(this.#fb, this.mode);
            this.initialFormValue = this.#customFormBuilder.getInitialFormValue(this.mode);
            this.formConfig = this.#customFormBuilder.getFormConfig();
            this.submitted.set(false);
        }
    }

    protected onSubmit(): void {
        this.submitted.set(true)
        console.log(this.form.value);
    }

    protected onClear(): void {
        this.submitted.set(false)
        this.form.reset(this.initialFormValue);
    }

    protected formControl(controlName: string): FormControl {
        return this.form.get(controlName) as FormControl;
    }
}
