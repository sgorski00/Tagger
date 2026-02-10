import {Injectable} from "@angular/core";

@Injectable({
    providedIn: 'root',
})
export class TextUtils {
    public getPrettyTags<T extends Iterable<string>>(tags: T): string {
        let tagsString = ''
        for(const tag of tags) {
            const formattedTag = tag.startsWith('#') ? tag : `#${tag}`;
            tagsString += `${formattedTag} `
        }
        return tagsString.trim();
    }
}