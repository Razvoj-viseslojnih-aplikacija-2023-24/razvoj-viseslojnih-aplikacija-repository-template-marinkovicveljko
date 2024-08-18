import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { KORISNICI_URL } from '../app.constants';
import { Korisnici } from '../models/korisnici';

@Injectable({
  providedIn: 'root'
})
export class KorisniciService {

  constructor(public httpClient: HttpClient) { }

  public getAllKorisnici(): Observable<any> {
    return this.httpClient.get(`${KORISNICI_URL}`);
  }

  public addKorisnik(korisnik: Korisnici): Observable<any> {
    return this.httpClient.post(`${KORISNICI_URL}`, korisnik);
  }

  public updateKorisnik(id: number,korisnik: Korisnici): Observable<any> {
    return this.httpClient.put(`${KORISNICI_URL}/id/${id}`, korisnik);
  }

  public deleteKorisnik(id:number) : Observable<any> {
    return this.httpClient.delete(`${KORISNICI_URL}/id/${id}`);
  }

}
