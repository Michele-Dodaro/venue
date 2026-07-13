import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { EventService } from '../../services/event.service';
import { EventDTOResponse } from '../../models/EventDTO';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-event-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './event-detail.html',
  styleUrl: './event-detail.css',
})
export class EventDetailComponent implements OnInit {
  event?: EventDTOResponse;
  isLoggedIn$!: Observable<boolean>;

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private eventService = inject(EventService);
  private cdr = inject(ChangeDetectorRef);
  private authService = inject(AuthService);

  ngOnInit() {
    this.isLoggedIn$ = this.authService.getAuthState();

    this.isLoggedIn$.subscribe(status => console.log('DEBUG Componente - Stato login:', status));

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.eventService.getEventById(Number(id)).subscribe({
        next: (data) => {
          this.event = data;
          this.cdr.detectChanges();
        },
        error: (err) => console.error('Error loading event:', err)
      });
    }
  }

  onDelete(id: number): void {
    if (confirm('Sei sicuro?')) {
      this.eventService.deleteEvent(id).subscribe({
        next: () => {
          alert('Eliminato!'); 
          this.router.navigate(['/events']);
        },
        error: (err) => console.error('Error:', err)
      });
    }
  }
}