export interface EventDTORequest {
  name: string;
  description: string;
  date: string;
  layoutId: number;
  active: boolean;
  genre: string;
  image: string;
}

export interface EventDTOResponse {
  id: number;
  name: string;
  description: string;
  date: Date;
  layoutId: number;
  active: boolean;
  genre: string;
  image: string;
  location?: string;
  contacts?: string;
}

export interface EventLayoutDTO {
  id?: number;
  conformation: string;
  rowField: number;
  number: number;
  price1: number;
  price2: number;
  price3: number;
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