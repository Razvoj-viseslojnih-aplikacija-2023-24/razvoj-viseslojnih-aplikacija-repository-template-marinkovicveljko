import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BANKA_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class BankaService {

  constructor(private httpClient: HttpClient) { }

  public getAllBanks(): Observable<any> {
    return this.httpClient.get(`${BANKA_URL}`);
  }
}
