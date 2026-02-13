import {inject, Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {ProfileResponse} from "../../../core/models";

@Injectable({
  providedIn: 'root',
})
export class ProfileHttpClient {
  readonly #BASE_API_URL = `${environment.apiUrl}`;
  readonly #http = inject(HttpClient);

  public getProfile(): Observable<ProfileResponse> {
    return this.#http.get<ProfileResponse>(`${this.#BASE_API_URL}/profile`).pipe(
        map(profile => ({
          ...profile,
          createdAt: new Date(profile.createdAt)
        }))
    );
  }
}
