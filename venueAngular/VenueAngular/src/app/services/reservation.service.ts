import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReservationDTORequest } from '../models/ReservationDTO';
import { ReservationDTOResponse } from '../models/ReservationDTO';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private apiUrl = 'http://localhost:8080/api/reservations';

  constructor(private http: HttpClient) { }

  createReservation(request: ReservationDTORequest): Observable<ReservationDTOResponse> {
    return this.http.post<ReservationDTOResponse>(this.apiUrl, request);
  }

  getAllReservations(): Observable<ReservationDTOResponse[]> {
    return this.http.get<ReservationDTOResponse[]>(this.apiUrl);
  }

  getReservationById(id: number): Observable<ReservationDTOResponse> {
    return this.http.get<ReservationDTOResponse>(`${this.apiUrl}/${id}`);
  }

  updateReservation(id: number, request: ReservationDTORequest): Observable<ReservationDTOResponse> {
    return this.http.put<ReservationDTOResponse>(`${this.apiUrl}/${id}`, request);
  }

  deleteReservation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}