import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GeneralGenerationRequest} from "./general-generation-request";
import {GenerationResponse} from "./generation-response";
import {Observable} from "rxjs";
import {ElectronicsGenerationsRequest} from "./electronics-generations-request";
import {ClothesGenerationsRequest} from "./clothes-generations-request";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root',
})
export class GenerationHttpClient {
  readonly #BASE_API_URL = `${environment.apiUrl}/tags`;
  http = inject(HttpClient)

  getGenerationResponse(data: GeneralGenerationRequest | ElectronicsGenerationsRequest | ClothesGenerationsRequest): Observable<GenerationResponse> {
    switch (data.mode) {
      case 'general':
        return this.fetchGeneralInfo(data as GeneralGenerationRequest);
      case 'clothes':
        return this.fetchClothesInfo(data as ClothesGenerationsRequest);
      case 'electronics':
        return this.fetchElectronicsInfo(data as ElectronicsGenerationsRequest);
      default: throw new Error(`Unsupported generation mode passed`);
    }
  }

  private fetchGeneralInfo(request: GeneralGenerationRequest): Observable<GenerationResponse> {
    return this.http.post<GenerationResponse>(`${this.#BASE_API_URL}/general`, request);
  }

  private fetchClothesInfo(request: ClothesGenerationsRequest): Observable<GenerationResponse> {
    return this.http.post<GenerationResponse>(`${this.#BASE_API_URL}/clothes`, request);
  }

  private fetchElectronicsInfo(request: ElectronicsGenerationsRequest): Observable<GenerationResponse> {
    return this.http.post<GenerationResponse>(`${this.#BASE_API_URL}/electronics`, request);
  }
}
