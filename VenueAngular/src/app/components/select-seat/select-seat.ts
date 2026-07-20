import { ChangeDetectionStrategy, Component, DestroyRef, OnInit, computed, inject, signal, ChangeDetectorRef } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { forkJoin, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { EventDTOResponse, EventLayoutDTO, TicketDTORequest, TicketDTOResponse } from '../../models/EventDTO';
import { AuthService } from '../../services/auth.service';
import { EventLayoutService } from '../../services/event-layout.service';
import { EventService } from '../../services/event.service';
import { TicketService } from '../../services/ticket.service';
import { PendingPaymentService } from '../../services/pending-payment.service';
import { CommonModule } from '@angular/common';

export interface Seat {
  row: number;
  column: number;
  status: 'available' | 'occupied' | 'selected';
  price?: bigint;
}

@Component({
  selector: 'app-select-seat',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './select-seat.html',
  styleUrl: './select-seat.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SelectSeat implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);
  private readonly eventService = inject(EventService);
  private readonly eventLayoutService = inject(EventLayoutService);
  private readonly ticketService = inject(TicketService);
  private readonly pendingPaymentService = inject(PendingPaymentService);
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
  readonly selectedSeatPrice = computed(() => {
    const seat = this.selectedSeat();
    return seat?.price ?? null;
  });

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

    const updatedGrid = this.seatsGrid().map(row =>
      row.map(s => {
        if (currentSeat && s.row === currentSeat.row && s.column === currentSeat.column) {
          return { ...s, status: 'available' as const };
        }
        if (s.row === seat.row && s.column === seat.column) {
          return { ...s, status: 'selected' as const };
        }
        return s;
      })
    );
    this.seatsGrid.set(updatedGrid);
    this.selectedSeat.set({ ...seat, status: 'selected', price: seat.price });
    this.successMessage.set(null);
  }

  confirmPurchase(): void {
    const seat = this.selectedSeat();
    const event = this.event();
    const layout = this.layout();

    if (!this.authService.isLoggedIn()) {
      this.errorMessage.set('Sessione non valida. Effettua di nuovo il login per completare l\'acquisto.');
      this.successMessage.set(null);
      return;
    }

    if (!seat || !event || !layout) {
      this.errorMessage.set('Errore nel caricamento dei dati. Riprova.');
      return;
    }

    this.pendingPaymentService.setPendingPayment({
      seat: { ...seat, status: 'selected', price: seat.price },
      event,
      layoutId: layout!.id ?? 0
    });

    this.router.navigate(['/payment']);
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

  getPriceClass(seat: Seat): string {
    const layout = this.layout();
    if (!layout || !seat.price) return '';
    
    if (seat.price === layout.price1) return 'price-1';
    if (seat.price === layout.price2) return 'price-2';
    if (seat.price === layout.price3) return 'price-3';
    
    return '';
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
    private seatKey(row: number, column: number): string {
    return `${row}-${column}`;
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
        const price = this.calculateSeatPrice(row, Number(layout.rowField), layout);

        return { row, column, status, price };
      });
    });
  }

  private calculateSeatPrice(row: number, totalRows: number, layout: EventLayoutDTO): bigint {
    const size1 = Math.floor(totalRows / 3);
    const size2 = Math.floor((totalRows - size1) / 2);
    
    if (row <= size1) {
      return layout.price1;
    } else if (row <= size1 + size2) {
      return layout.price2;
    } else {
      return layout.price3;
    }
  }
}