import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TicketDTORequest, TicketDTOResponse } from '../models/EventDTO';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private apiUrl = 'http://localhost:8080/api/tickets';

  constructor(private http: HttpClient) { }

  buyTicket(request: TicketDTORequest): Observable<TicketDTOResponse> {
    return this.http.post<TicketDTOResponse>(this.apiUrl, request);
  }

  getTicketsByLayout(layoutId: number): Observable<TicketDTOResponse[]> {
    return this.http.get<TicketDTOResponse[]>(`${this.apiUrl}/layout/${layoutId}`);
  }
}