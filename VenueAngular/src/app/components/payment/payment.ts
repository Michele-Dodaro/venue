import { Component, inject, OnInit, signal, computed } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { DestroyRef } from '@angular/core';
import { PendingPaymentService, PendingPayment } from '../../services/pending-payment.service';
import { TicketService } from '../../services/ticket.service';
import { EmailService } from '../../services/email.service';
import { TicketDTORequest } from '../../models/EventDTO';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './payment.html',
  styleUrl: './payment.css',
})
export class Payment implements OnInit {
  private readonly pendingPaymentService = inject(PendingPaymentService);
  private readonly ticketService = inject(TicketService);
  private readonly emailService = inject(EmailService);
  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);

  readonly pendingPayment = signal<PendingPayment | null>(null);
  readonly isProcessing = signal(false);
  readonly errorMessage = signal<string | null>(null);
  readonly successMessage = signal<string | null>(null);
  readonly canProcess = computed(() => this.pendingPayment() !== null && !this.isProcessing() && this.email() !== '');
  readonly totalPrice = computed(() => {
    const payment = this.pendingPayment();
    const price = payment?.seat?.price;
    return price ? Number(price) : 0;
  });

  // Simulated payment form data
  readonly email = signal('');
  readonly cardNumber = signal('');
  readonly expiryDate = signal('');
  readonly cvv = signal('');
  readonly cardholderName = signal('');

  ngOnInit(): void {
    this.pendingPayment.set(this.pendingPaymentService.getPendingPayment()());
    
    if (!this.pendingPayment()) {
      this.router.navigate(['/events']);
    }
  }

  processPayment(): void {
    // Validate form
    if (!this.cardNumber() || !this.expiryDate() || !this.cvv() || !this.cardholderName()) {
      this.errorMessage.set('Completa tutti i dati della carta di credito.');
      return;
    }

    if (!this.email()) {
      this.errorMessage.set('Inserisci un indirizzo email valido.');
      return;
    }

    this.isProcessing.set(true);
    this.errorMessage.set(null);
    this.successMessage.set(null);

    const payment = this.pendingPayment();
    if (!payment) {
      this.errorMessage.set('Errore nel caricamento dei dati di pagamento.');
      this.isProcessing.set(false);
      return;
    }

    const ticketRequest: TicketDTORequest = {
      rowField: payment.seat.row,
      columnField: payment.seat.column,
      layoutId: payment.layoutId
    };

    // Simulate payment processing delay
    setTimeout(() => {
      this.ticketService.buyTicket(ticketRequest)
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe({
          next: () => {
            this.errorMessage.set(null);
            
            // Send email with QR code
            this.emailService.sendReservationEmailWithQR(
              this.email(),
              payment.event.name,
              new Date(payment.event.date).toLocaleString('it-IT'),
              payment.seat.row,
              payment.seat.column,
              Number(payment.seat.price).toString()
            )
              .pipe(takeUntilDestroyed(this.destroyRef))
              .subscribe({
                next: () => {
                  this.successMessage.set('Pagamento completato! Il tuo biglietto è stato inviato via email.');
                  this.pendingPaymentService.clearPendingPayment();
                  
                  // Redirect after success
                  setTimeout(() => {
                    this.router.navigate(['/events', payment.event.id]);
                  }, 2000);
                },
                error: (emailError) => {
                  console.error('Errore invio email:', emailError);
                  console.error('Dettagli errore:', emailError.status, emailError.message);
                  this.successMessage.set('Pagamento completato! Controlla la tua email per il biglietto.');
                  this.pendingPaymentService.clearPendingPayment();
                  
                  setTimeout(() => {
                    this.router.navigate(['/events', payment.event.id]);
                  }, 2000);
                }
              });
          },
          error: (error: HttpErrorResponse) => {
            this.isProcessing.set(false);
            if (error.status === 403) {
              this.errorMessage.set('Non sei autorizzato ad acquistare posti. Effettua il login con un account valido.');
            } else {
              this.errorMessage.set('Il posto potrebbe essere appena stato acquistato da un altro utente. Riprova con un altro posto.');
            }
            this.successMessage.set(null);
          }
        });
    }, 1000);
  }

  cancelPayment(): void {
    this.pendingPaymentService.clearPendingPayment();
    const payment = this.pendingPayment();
    if (payment) {
      this.router.navigate(['/events', payment.event.id, 'seats']);
    } else {
      this.router.navigate(['/events']);
    }
  }
}
