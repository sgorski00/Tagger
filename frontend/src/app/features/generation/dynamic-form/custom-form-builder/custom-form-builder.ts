import {Injectable} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {FormMode} from "../form-mode";
import {FormConfig} from "../form-config";
import {ClothesFormValue, ElectronicsFormValue, FormValue, GeneralFormValue} from "../form-value";

@Injectable({
    providedIn: 'root',
})
export class CustomFormBuilder {
    readonly #tagsQuantityMin = 1;
    readonly #tagsQuantityMax = 30;
    readonly #monthsOfWarrantyMin = 0;
    readonly #monthsOfWarrantyMax = 120;
    readonly #itemNameMinLength = 3;
    readonly #platforms: ReadonlyArray<string> = ['amazon', 'ebay', 'vinted'];
    readonly #responseStyles: ReadonlyArray<string> = ['formal', 'casual', 'humorous'];

    readonly #initialFormValue: GeneralFormValue = {
        item: '',
        tagsQuantity: 10,
        platform: this.#platforms[0],
        targetAudience: '',
        responseStyle: this.#responseStyles[0],
    }

    readonly #clothesFormValue: Omit<ClothesFormValue, keyof GeneralFormValue> = {
        color: '',
        size: '',
        material: ''
    }

    readonly #electronicsFormValue: Omit<ElectronicsFormValue, keyof GeneralFormValue> = {
        model: '',
        color: '',
        monthsOfWarranty: null
    }

    public buildForm(fb: FormBuilder, mode: FormMode) {
        switch (mode) {
            case 'general':
                return this.#buildGeneralForm(fb);
            case "clothes":
                return this.#buildClothesForm(fb);
            case "electronics":
                return this.#buildElectronicsForm(fb);
            default:
                throw new Error(`Unsupported form mode: ${mode}`);
        }
    }

    public getInitialFormValue(mode: FormMode): FormValue {
        switch (mode) {
            case 'general':
                return this.#initialFormValue;
            case "clothes":
                return {...this.#initialFormValue, ...this.#clothesFormValue};
            case "electronics":
                return {...this.#initialFormValue, ...this.#electronicsFormValue};
            default:
                throw new Error(`Unsupported form mode: ${mode}`);
        }
    }

    public getFormConfig(): FormConfig {
        return {
            tagsQuantityMin: this.#tagsQuantityMin,
            tagsQuantityMax: this.#tagsQuantityMax,
            monthsOfWarrantyMin: this.#monthsOfWarrantyMin,
            monthsOfWarrantyMax: this.#monthsOfWarrantyMax,
            itemNameMinLength: this.#itemNameMinLength,
            responseStyles: this.#responseStyles,
            platforms: this.#platforms
        }
    }

    #buildGeneralForm(fb: FormBuilder) {
        return fb.group({
            item: fb.control<string>(this.#initialFormValue.item, [Validators.required, Validators.minLength(this.#itemNameMinLength)]),
            tagsQuantity: fb.control<number | null>(this.#initialFormValue.tagsQuantity, [
                Validators.min(this.#tagsQuantityMin),
                Validators.max(this.#tagsQuantityMax)
            ]),
            platform: fb.control<string>(this.#initialFormValue.platform),
            targetAudience: fb.control<string>(this.#initialFormValue.targetAudience, Validators.required),
            responseStyle: fb.control<string>(this.#initialFormValue.responseStyle, Validators.required)
        })
    }

    #buildClothesForm(fb: FormBuilder) {
        const generalForm = this.#buildGeneralForm(fb)
        return fb.group({
            ...generalForm.controls,
            color: fb.control<string>(this.#clothesFormValue.color),
            size: fb.control<string>(this.#clothesFormValue.size),
            material: fb.control<string>(this.#clothesFormValue.material)
        });
    }

    #buildElectronicsForm(fb: FormBuilder) {
        const generalForm = this.#buildGeneralForm(fb)
        return fb.group({
            ...generalForm.controls,
            model: fb.control<string>(this.#electronicsFormValue.model),
            color: fb.control<string>(this.#electronicsFormValue.color),
            monthsOfWarranty: fb.control<number | null>(this.#electronicsFormValue.monthsOfWarranty)
        })
    }
}
