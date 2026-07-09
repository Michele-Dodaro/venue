import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EventDTO } from '../models/EventLayoutDTO';

@Injectable({
  providedIn: 'root'
})
export class EventLayoutService {
  private apiUrl = 'http://localhost:8080/api/event-layouts';

  constructor(private http: HttpClient) { }

  createEventLayout(layout: EventDTO): Observable<EventDTO> {
    return this.http.post<EventDTO>(this.apiUrl, layout);
  }

  getAllEventLayouts(): Observable<EventDTO[]> {
    return this.http.get<EventDTO[]>(this.apiUrl);
  }

  getEventLayoutById(id: number): Observable<EventDTO> {
    return this.http.get<EventDTO>(`${this.apiUrl}/${id}`);
  }

  updateEventLayout(id: number, layout: EventDTO): Observable<EventDTO> {
    return this.http.put<EventDTO>(`${this.apiUrl}/${id}`, layout);
  }

  deleteEventLayout(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}