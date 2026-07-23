import { Component, OnInit, ChangeDetectorRef, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { EventService } from '../../services/event.service';
import { EventDTOResponse } from '../../models/EventDTO';
import { inject } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { AddButtonComponent } from '../add-button/add-button';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './event-list.html',
  styleUrls: ['./event-list.css']
})
export class EventListComponent implements OnInit {
  events: EventDTOResponse[] = [];
  private authService = inject(AuthService);
  currentPage: number = 1;
  itemsPerPage: number = 5;

  get visibleEvents(): EventDTOResponse[] {
    return this.events.filter(event => event.active || this.isLoggedIn());
  }

  get totalPages(): number {
    return Math.ceil(this.visibleEvents.length / this.itemsPerPage);
  }

  get paginatedEvents(): EventDTOResponse[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.visibleEvents.slice(startIndex, endIndex);
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  readonly isModalOpen = signal(false);
  readonly venueLocation = 'Via Roma 123, Milano';
  readonly venueContacts = 'Email: info@venue.it | Tel: +39 02 1234 5678';
  searchKeyword: string = '';

  onSearch(): void {
    if (this.searchKeyword && this.searchKeyword.trim() !== '') {
      this.eventService.searchEvents(this.searchKeyword.trim()).subscribe({
        next: (data) => {
          this.events = data;
          this.currentPage = 1;
        },
        error: (err) => console.error(err)
      });
    } else {
      this.loadEvents();
    }
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  logout() {
    this.authService.logout();
    window.location.reload();
  }

  openModal(): void {
    this.isModalOpen.set(true);
  }

  closeModal(): void {
    this.isModalOpen.set(false);
  }

  constructor(
    private eventService: EventService,
    private cdr: ChangeDetectorRef
  ) { }

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
        this.currentPage = 1;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error:', err)
    });
  }
}