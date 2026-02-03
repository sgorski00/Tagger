import {inject, Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GenerationResponse} from "../../generation/generation-response";

@Injectable({
    providedIn: 'root',
})
export class HistoryHttpClient {
    readonly #BASE_API_URL = `${environment.apiUrl}`;
    readonly #http = inject(HttpClient)

    getRequestsHistory(): Observable<readonly GenerationResponse[]> {
        return this.#http.get<readonly GenerationResponse[]>(`${this.#BASE_API_URL}/tags/history`);
    }
}
