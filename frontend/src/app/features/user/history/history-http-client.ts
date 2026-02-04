import {inject, Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GenerationResponse} from "../../generation/generation-response";
import {PageResponse} from "../../../core/models/page-response";

@Injectable({
    providedIn: 'root',
})
export class HistoryHttpClient {
    readonly #BASE_API_URL = `${environment.apiUrl}`;
    readonly #http = inject(HttpClient)

    getRequestsHistory(page: number = 1, size: number = 10): Observable<PageResponse<GenerationResponse>>{
        const params = {
            page: page.toString(),
            size: size.toString()
        };
        return this.#http.get<PageResponse<GenerationResponse>>(`${this.#BASE_API_URL}/tags/history`, {params});
    }
}
