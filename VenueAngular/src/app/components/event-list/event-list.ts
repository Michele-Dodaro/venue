import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router'; 
import { EventService } from '../../services/event.service'; 
import { EventDTOResponse } from '../../models/EventDTO'; 
import { inject } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { AddButtonComponent } from '../add-button/add-button';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './event-list.html',
  styleUrl: './event-list.css'
})

export class EventListComponent implements OnInit {
  events: EventDTOResponse[] = [];
  private authService = inject(AuthService);

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }
  logout() {
    this.authService.logout();
    window.location.reload();
  }
  constructor(
    private eventService: EventService,
    private cdr: ChangeDetectorRef 
  ) {}

  ngOnInit(): void {
    const savedEvents = localStorage.getItem('events_cache');
    if (savedEvents) {
      this.events = JSON.parse(savedEvents);
    }
    this.loadEvents();
  }

  loadEvents(): void {
    this.eventService.getAllEvents().subscribe({
      next: (data) => {
        this.events = data;
        localStorage.setItem('events_cache', JSON.stringify(data));
        this.cdr.detectChanges(); 
      },
      error: (err) => console.error('Error:', err)
    });
  }
}