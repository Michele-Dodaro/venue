import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventService } from '../../services/event.service'; 
import { EventDTOResponse } from '../../models/EventDTO'; 

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './event-list.html', 
  styleUrl: './event-list.css' 
})
export class EventListComponent implements OnInit {
  events: EventDTOResponse[] = [];

  constructor(
    private eventService: EventService,
    private cdr: ChangeDetectorRef 
  ) {}

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.eventService.getAllEvents().subscribe({
      next: (data) => {
        this.events = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Errore:', err)
    });
  }
}