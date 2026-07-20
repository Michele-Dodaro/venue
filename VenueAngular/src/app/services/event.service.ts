import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EventDTORequest } from '../models/EventDTO';
import { EventDTOResponse } from '../models/EventDTO';
import { EventLayoutDTO } from '../models/EventDTO';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private apiUrl = 'http://localhost:8080/api/events';

  constructor(private http: HttpClient) { }

  getAllEvents(): Observable<EventDTOResponse[]> {
    return this.http.get<EventDTOResponse[]>(this.apiUrl);
  }
  
  getEventById(id: number): Observable<EventDTOResponse> {
    return this.http.get<EventDTOResponse>(`${this.apiUrl}/${id}`);
  }

 createEvent(event: EventDTORequest): Observable<EventDTOResponse> {
    return this.http.post<EventDTOResponse>(this.apiUrl, event);
  }

  updateEvent(id: number, event: EventDTORequest): Observable<EventDTOResponse> {
    return this.http.put<EventDTOResponse>(`${this.apiUrl}/modifica/${id}`, event);
  }

  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  
  getLayouts(): Observable<EventLayoutDTO[]> {
    return this.http.get<EventLayoutDTO[]>('http://localhost:8080/api/event-layouts');
  }

}