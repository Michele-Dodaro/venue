import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router'; 
import { EventService } from '../../services/event.service'; 
import { EventDTOResponse } from '../../models/EventDTO'; 
import { inject } from '@angular/core';
import { AuthService } from '../../services/auth.service';

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