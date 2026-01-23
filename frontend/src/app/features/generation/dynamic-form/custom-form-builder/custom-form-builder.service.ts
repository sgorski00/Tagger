import {Injectable} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {FormMode} from "../form-mode";

@Injectable({
    providedIn: 'root',
})
class CustomFormBuilder {
    readonly #tagsQuantityMin = 1;
    readonly #tagsQuantityMax = 30;
    readonly #itemNameMinLength = 3;
    protected readonly platforms: Array<string> = ['amazon', 'ebay', 'vinted'];
    protected readonly responseStyles: Array<string> = ['formal', 'casual', 'humorous'];

    readonly #initialFormValue = {
        item: '',
        tagsQuantity: 10,
        platform: this.platforms[0],
        targetAudience: '',
        responseStyle: this.responseStyles[0],
    }

    public buildForm(fb: FormBuilder, mode: FormMode) {
        switch (mode) {
            case 'general':
                return this.#buildGeneralForm(fb);
            // Add other cases for different form modes as needed
            default:
                throw new Error(`Unsupported form mode: ${mode}`);
        }
    }

    public getInitialFormValue(mode: FormMode) {
        switch (mode) {
            case 'general':
                return this.#initialFormValue;
            // Add other cases for different form modes as needed
            default:
                throw new Error(`Unsupported form mode: ${mode}`);
        }
    }

    public getFormConfig() {
        return {
            tagsQuantityMin: this.#tagsQuantityMin,
            tagsQuantityMax: this.#tagsQuantityMax,
            itemNameMinLength: this.#itemNameMinLength,
            responseStyles: this.responseStyles,
            platforms: this.platforms
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
}

export default CustomFormBuilder
