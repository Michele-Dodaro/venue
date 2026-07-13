export interface EventDTORequest {
  name: string;
  description: string;
  date: Date;
}

export interface EventDTOResponse {
  id: number;
  name: string;
  description: string;
  date: Date;
}