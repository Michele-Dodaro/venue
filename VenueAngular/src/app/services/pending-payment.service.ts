import { Injectable, signal } from '@angular/core';
import { Seat } from '../components/select-seat/select-seat';
import { EventDTOResponse } from '../models/EventDTO';

export interface PendingPayment {
  seat: Seat;
  event: EventDTOResponse;
  layoutId: number;
}

@Injectable({
  providedIn: 'root'
})
export class PendingPaymentService {
  private pendingPayment = signal<PendingPayment | null>(null);

  setPendingPayment(payment: PendingPayment): void {
    this.pendingPayment.set(payment);
  }

  getPendingPayment() {
    return this.pendingPayment.asReadonly();
  }

  clearPendingPayment(): void {
    this.pendingPayment.set(null);
  }
}
