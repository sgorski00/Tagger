import {Component, inject, input, signal, effect, output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {FormMode} from "../form-mode";
import {CustomFormBuilder} from "../custom-form-builder/custom-form-builder";
import {TextInput} from "./inputs/text-input/text-input";
import {NumberInput} from "./inputs/number-input/number-input";
import {SelectInput} from "./inputs/select-input/select-input";
import {GeneralGenerationRequest} from "../../general-generation-request";
import {ElectronicsGenerationsRequest} from "../../electronics-generations-request";
import {ClothesGenerationsRequest} from "../../clothes-generations-request";

@Component({
    selector: 'app-form-shell',
    imports: [
        ReactiveFormsModule,
        TextInput,
        NumberInput,
        SelectInput
    ],
    templateUrl: './form-shell.html',
    styleUrl: './form-shell.scss',
})
export class FormShell {
    readonly mode = input.required<FormMode>();
    readonly loading = input.required<boolean>();
    readonly formSubmit = output<GeneralGenerationRequest | ElectronicsGenerationsRequest | ClothesGenerationsRequest>();
    readonly #customFormBuilder = inject(CustomFormBuilder);
    readonly #fb = new FormBuilder();
    protected readonly form = signal<FormGroup | null>(null);
    protected readonly initialFormValue = signal<any | null>(null);
    protected readonly formConfig = signal<any | null>(null);
    protected readonly submitted = signal<boolean>(false);

    constructor() {
        effect(() => {
            const currentMode = this.mode();
            if(currentMode) {
                this.form.set(this.#customFormBuilder.buildForm(this.#fb, currentMode));
                this.initialFormValue.set(this.#customFormBuilder.getInitialFormValue(currentMode));
                this.formConfig.set(this.#customFormBuilder.getFormConfig());
                this.submitted.set(false);
            }
        })
    }

    protected onSubmit(): void {
        const data = {
            ...this.form()?.value,
            mode: this.mode()
        };
        console.log("Submitting form:", data);
        this.submitted.set(true);
        this.formSubmit.emit(data);
    }

    protected onClear(): void {
        this.submitted.set(false)
        this.form()?.reset(this.initialFormValue);
    }

    protected formControl(controlName: string): FormControl {
        const form = this.form();
        if (!form) throw new Error('Form is not initialized');
        return form.get(controlName) as FormControl;
    }
}
