import { ChangeDetectionStrategy, Component, DestroyRef, OnInit, computed, inject, signal, ChangeDetectorRef } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { forkJoin, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { EventDTOResponse, EventLayoutDTO, TicketDTORequest, TicketDTOResponse } from '../../models/EventDTO';
import { AuthService } from '../../services/auth.service';
import { EventLayoutService } from '../../services/event-layout.service';
import { EventService } from '../../services/event.service';
import { TicketService } from '../../services/ticket.service';

export interface Seat {
  row: number;
  column: number;
  status: 'available' | 'occupied' | 'selected';
}

@Component({
  selector: 'app-select-seat',
  templateUrl: './select-seat.html',
  styleUrl: './select-seat.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SelectSeat implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly eventService = inject(EventService);
  private readonly eventLayoutService = inject(EventLayoutService);
  private readonly ticketService = inject(TicketService);
  private readonly destroyRef = inject(DestroyRef);
  private readonly cdr = inject(ChangeDetectorRef);

  readonly event = signal<EventDTOResponse | null>(null);
  readonly layout = signal<EventLayoutDTO | null>(null);
  readonly seatsGrid = signal<Seat[][]>([]);
  readonly selectedSeat = signal<Seat | null>(null);
  readonly isLoading = signal(true);
  readonly errorMessage = signal<string | null>(null);
  readonly successMessage = signal<string | null>(null);
  readonly canConfirm = computed(() => this.selectedSeat() !== null && !this.isLoading());

  ngOnInit(): void {
    const eventId = Number(this.route.snapshot.paramMap.get('id'));

    if (!Number.isFinite(eventId) || eventId <= 0) {
      this.isLoading.set(false);
      this.errorMessage.set('Id evento non valido.');
      return;
    }

    this.loadSeatMap(eventId);
  }

  selectSeat(seat: Seat): void {
    if (seat.status === 'occupied') {
      return;
    }

    const currentSeat = this.selectedSeat();

    // Se clicco lo stesso posto, lo deseleziono
    if (currentSeat && currentSeat.row === seat.row && currentSeat.column === seat.column) {
      const updatedGrid = this.seatsGrid().map(row =>
        row.map(s =>
          s.row === seat.row && s.column === seat.column
            ? { ...s, status: 'available' as const }
            : s
        )
      );
      this.seatsGrid.set(updatedGrid);
      this.selectedSeat.set(null);
      return;
    }

    // Aggiorna la griglia: deseleziona il precedente e seleziona il nuovo
    const updatedGrid = this.seatsGrid().map(row =>
      row.map(s => {
        // Deseleziona il precedente
        if (currentSeat && s.row === currentSeat.row && s.column === currentSeat.column) {
          return { ...s, status: 'available' as const };
        }
        // Seleziona il nuovo
        if (s.row === seat.row && s.column === seat.column) {
          return { ...s, status: 'selected' as const };
        }
        return s;
      })
    );
    this.seatsGrid.set(updatedGrid);
    this.selectedSeat.set({ ...seat, status: 'selected' });
    this.successMessage.set(null);
  }

  confirmPurchase(): void {
    const seat = this.selectedSeat();
    const event = this.event();

    if (!this.authService.isLoggedIn()) {
      this.errorMessage.set('Sessione non valida. Effettua di nuovo il login per completare l\'acquisto.');
      this.successMessage.set(null);
      return;
    }

    if (!seat || !event) {
     console.log('Errore: seat o event null');
     return;
   }

   console.log('Bottone cliccato - Posto:', seat.row, seat.column);
    
   // Cambia il posto a rosso IMMEDIATAMENTE
   const updatedGrid = this.seatsGrid().map(row =>
     row.map(s => {
       if (s.row === seat.row && s.column === seat.column) {
         console.log('Cambio posto a occupied');
         return { row: s.row, column: s.column, status: 'occupied' as const };
       }
       return s;
     })
   );
   console.log('Grid aggiornata');
   this.seatsGrid.set(updatedGrid);
   this.cdr.markForCheck();
   this.selectedSeat.set(null);

   // Poi invia la richiesta al server
   const request: TicketDTORequest = {
     rowField: seat.row,
     columnField: seat.column,
     layoutId: this.layout()?.id ?? 0
   };

   console.log('Invio request ticket:', JSON.stringify(request));

   this.ticketService.buyTicket(request)
     .pipe(takeUntilDestroyed(this.destroyRef))
     .subscribe({
       next: () => {
         this.errorMessage.set(null);
         this.successMessage.set('Acquisto completato con successo.');
       },
       error: (error: HttpErrorResponse) => {
         if (error.status === 403) {
           this.errorMessage.set('Non sei autorizzato ad acquistare posti. Effettua il login con un account valido.');
           this.successMessage.set(null);
           this.loadSeatMap(event.id);
           return;
         }

         // Se l'acquisto fallisce, recarica la mappa per sincronizzare lo stato
         this.errorMessage.set('Il posto potrebbe essere appena stato acquistato da un altro utente.');
         this.successMessage.set(null);
         this.loadSeatMap(event.id);
       }
     });
  }

  seatLabel(seat: Seat): string {
    if (seat.status === 'occupied') {
      return 'occupato';
    }

    if (seat.status === 'selected') {
      return 'selezionato';
    }

    return 'disponibile';
  }

  private loadSeatMap(eventId: number): void {
    this.isLoading.set(true);
    this.errorMessage.set(null);
    this.successMessage.set(null);
    this.selectedSeat.set(null);

    this.eventService.getEventById(eventId).pipe(
      switchMap((event) => forkJoin({
        event: of(event),
        layout: this.eventLayoutService.getEventLayoutById(event.layoutId),
        tickets: this.ticketService.getTicketsByLayout(event.layoutId)
      })),
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: ({ event, layout, tickets }) => {
        this.event.set(event);
        this.layout.set(layout);
        this.seatsGrid.set(this.buildSeatsGrid(layout, tickets));
        this.isLoading.set(false);
      },
      error: (error: HttpErrorResponse) => {
        this.event.set(null);
        this.layout.set(null);
        this.seatsGrid.set([]);
        this.isLoading.set(false);

        this.errorMessage.set('Impossibile caricare la mappa dei posti.');
      }
    });
  }

  private buildSeatsGrid(layout: EventLayoutDTO, soldTickets: TicketDTOResponse[]): Seat[][] {
    const occupiedSeats = new Set(
      soldTickets.map((ticket) => this.seatKey(ticket.rowField, ticket.columnField))
    );

    return Array.from({ length: Number(layout.rowField) }, (_, rowIndex) => {
      const row = rowIndex + 1;

      return Array.from({ length: Number(layout.number) }, (_, seatIndex) => {
        const column = seatIndex + 1;
        const status = occupiedSeats.has(this.seatKey(row, column)) ? 'occupied' : 'available';

        return { row, column, status };
      });
    });
  }

  private seatKey(row: number, column: number): string {
    return `${row}-${column}`;
  }
}