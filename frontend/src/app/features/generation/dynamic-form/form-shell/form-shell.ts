import {Component, inject, Input, signal, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {TitleCasePipe} from "@angular/common";
import {FormMode} from "../form-mode";
import {CustomFormBuilder} from "../custom-form-builder/custom-form-builder.service";

@Component({
    selector: 'app-form-shell',
    imports: [
        ReactiveFormsModule,
        TitleCasePipe
    ],
    templateUrl: './form-shell.html',
    styleUrl: './form-shell.scss',
})
export class FormShell implements OnInit{
    @Input({required: true}) mode!: FormMode;
    readonly #customFormBuilder = inject(CustomFormBuilder);
    readonly #fb = new FormBuilder();
    protected form!: FormGroup;
    protected initialFormValue!: any;
    protected formConfig!: any;
    protected readonly submitted = signal(false);

    ngOnInit() {
        this.form = this.#customFormBuilder.buildForm(this.#fb, this.mode);
        this.initialFormValue = this.#customFormBuilder.getInitialFormValue(this.mode);
        this.formConfig = this.#customFormBuilder.getFormConfig();
    }

    protected onSubmit(): void {
        this.submitted.set(true)
        console.log(this.form.value); //todo: clothes/electronics form not sending with all fields
    }

    protected onClear(): void {
        this.submitted.set(false)
        this.form.reset(this.initialFormValue);
    }
}
