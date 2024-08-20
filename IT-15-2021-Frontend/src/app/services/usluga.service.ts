import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { USLUGA_URL, USLUGA_ZA_KORISNIKA_URL } from '../app.constants';
import { Usluga } from '../models/usluga';

@Injectable({
  providedIn: 'root'
})
export class UslugaService {

  constructor(private  httpClient : HttpClient) { }

  public getAllUslugeZaKorisnika(idKorisnika: number): Observable<any> {
    return this.httpClient.get(`${USLUGA_ZA_KORISNIKA_URL}/${idKorisnika}`);
  }

  public addUsluga(usluga: Usluga): Observable<any> {
    return this.httpClient.post(`${USLUGA_URL}`, usluga);
  }

  public updateUsluga(id: number,usluga: Usluga): Observable<any> {
    return this.httpClient.put(`${USLUGA_URL}/id/${id}`, usluga);
  }

  public deleteUsluga(id:number) : Observable<any> {
    return this.httpClient.delete(`${USLUGA_URL}/id/${id}`);
  }
}
