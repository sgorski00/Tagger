import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GenerationResponse} from "./generation-response";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {GenerationRequest, GeneralGenerationRequest, ClothesGenerationRequest, ElectronicsGenerationRequest} from "./generation-request.types";

@Injectable({
  providedIn: 'root',
})
export class GenerationHttpClient {
  readonly #BASE_API_URL = `${environment.apiUrl}/tags`;
  readonly #http = inject(HttpClient)

  getGenerationResponse(data: GenerationRequest): Observable<GenerationResponse> {
    switch (data.mode) {
      case 'general':
        return this.fetchGeneralInfo(data);
      case 'clothes':
        return this.fetchClothesInfo(data);
      case 'electronics':
        return this.fetchElectronicsInfo(data);
      default: throw new Error(`Unsupported generation mode passed`);
    }
  }

  private fetchGeneralInfo(request: GeneralGenerationRequest): Observable<GenerationResponse> {
    return this.#http.post<GenerationResponse>(`${this.#BASE_API_URL}/general`, request);
  }

  private fetchClothesInfo(request: ClothesGenerationRequest): Observable<GenerationResponse> {
    return this.#http.post<GenerationResponse>(`${this.#BASE_API_URL}/clothes`, request);
  }

  private fetchElectronicsInfo(request: ElectronicsGenerationRequest): Observable<GenerationResponse> {
    return this.#http.post<GenerationResponse>(`${this.#BASE_API_URL}/electronics`, request);
  }
}
