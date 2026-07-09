import { EventDTOResponse } from './EventDTO';

export interface EventDTO {
    id?: number;
    conformation: string;
    rowField: string;
    number: number;
    price1: bigint;
    price2: bigint;
    price3: bigint;
    enteId: EventDTOResponse[];
}