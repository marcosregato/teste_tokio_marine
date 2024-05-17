import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TransferService {
  private apiUrl = 'http://localhost:8085/transfers';

  constructor(private http: HttpClient) {}

  scheduleTransfer(data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/schedule`, data);
  }
  getTransferData(data: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/all`, data);
  }
}
