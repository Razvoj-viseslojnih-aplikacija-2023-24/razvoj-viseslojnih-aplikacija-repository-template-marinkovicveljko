import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BANKA_URL } from '../app.constants';
import { Banka } from '../models/banka';

@Injectable({
  providedIn: 'root'
})
export class BankaService {

  constructor(private httpClient: HttpClient) { }

  public getAllBanks(): Observable<any> {
    return this.httpClient.get(`${BANKA_URL}`);
  }

  public addBanka(banka: Banka): Observable<any> {
    return this.httpClient.post(`${BANKA_URL}`, banka)
  }


  public updateBanka(id: number, banka: Banka): Observable<any> {
    return this.httpClient.put(`${BANKA_URL}/id/${id}`, banka)
  }

  public deleteBanka(id: number): Observable<any> {
    return this.httpClient.delete(`${BANKA_URL}/id/${id}`, {
      responseType: 'text',
    })
  }

}
