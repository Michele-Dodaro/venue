export interface EventDTORequest {
  name: string;
  description: string;
  date: string;
  layoutId: number;
  active: boolean;
  genre: string;
}

export interface EventDTOResponse {
  id: number;
  name: string;
  description: string;
  date: Date;
  layoutId: number;
  active: boolean;
  genre: string;

}

export interface EventLayoutDTO {
  id?: number;
  conformation: string;
  rowField: number;
  number: number;
  price1: bigint;
  price2: bigint;
  price3: bigint;
  eventId: number; 
}

export interface TicketDTORequest {
    rowField: number;
    columnField: number;
    layoutId: number;
}

export interface TicketDTOResponse {
    id: number;
    rowField: number;
    columnField: number;
    layoutId: number;
    available: boolean;
}