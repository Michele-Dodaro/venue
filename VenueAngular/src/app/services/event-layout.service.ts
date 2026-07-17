import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EventLayoutDTO } from '../models/EventDTO';

@Injectable({
  providedIn: 'root'
})
export class EventLayoutService {
  private apiUrl = 'http://localhost:8080/api/event-layouts';

  constructor(private http: HttpClient) { }

  createEventLayout(layout: EventLayoutDTO): Observable<EventLayoutDTO> {
    return this.http.post<EventLayoutDTO>(this.apiUrl, layout);
  }

  getAllEventLayouts(): Observable<EventLayoutDTO[]> {
    return this.http.get<EventLayoutDTO[]>(`${this.apiUrl}/posti`);
  }

  getEventLayoutById(id: number): Observable<EventLayoutDTO> {
    return this.http.get<EventLayoutDTO>(`${this.apiUrl}/${id}`);
  }

  updateEventLayout(id: number, layout: EventLayoutDTO): Observable<EventLayoutDTO> {
    return this.http.put<EventLayoutDTO>(`${this.apiUrl}/${id}`, layout);
  }

  deleteEventLayout(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}