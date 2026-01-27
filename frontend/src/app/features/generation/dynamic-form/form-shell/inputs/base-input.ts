import {Directive, input} from "@angular/core";
import {FormControl} from "@angular/forms";

@Directive({
    selector: '[appBaseInput]',
})
export abstract class BaseInput {
    readonly label = input.required<string>();
    readonly control = input.required<FormControl>();
    readonly controlId = input.required<string>();
    readonly isSubmitted = input.required<boolean>();
    readonly isOptional = input<boolean>(false);
    readonly placeholder = input<string>('');

    protected get isError(): boolean {
        return this.control().invalid && (this.control().dirty || this.control().touched || this.isSubmitted());
    }
}