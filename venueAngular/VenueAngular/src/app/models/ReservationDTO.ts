import { EventDTOResponse } from './EventDTO';

export interface ReservationDTORequest {
  eventId: EventDTOResponse[];
  customerName: string;
  customerEmail: string;
  customerPhone: string;
  numberOfParticipants: number;
  totalAmount: number;
  paymentStatus: string;
  reservationStatus: string;
}

export interface ReservationDTOResponse {
  id: number;
  eventId: number;
  customerName: string;
  customerEmail: string;
  customerPhone: string;
  numberOfParticipants: number;
  totalAmount: number;
  paymentStatus: string;
  reservationStatus: string;
  creationTimestamp: string;
}