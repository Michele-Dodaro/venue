import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ReservationEmail {
  email: string;
  eventName: string;
  eventDate: string;
  fila: number;
  posto: number;
  price: string;
}

@Injectable({
  providedIn: 'root'
})
export class EmailService {
  private apiUrl = 'http://localhost:8080/api/email';

  constructor(private http: HttpClient) { }

  sendReservationEmailWithQR(email: string, eventName: string, eventDate: string, fila: number, posto: number, price: string): Observable<any> {
    const request = {
      email,
      eventName,
      eventDate,
      fila,
      posto,
      price
    };
    return this.http.post(`${this.apiUrl}/send-ticket`, request);
  }
}
