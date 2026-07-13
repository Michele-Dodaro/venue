import { Routes } from '@angular/router';
import { EventListComponent } from './components/event-list/event-list';
import { LoginComponent } from './components/login/login';
import { EventDetailComponent } from './components/event-detail/event-detail';
import { CreateEventComponent } from './components/event-creation/event-creation';

export const routes: Routes = [
    {
        path: 'events',
        component: EventListComponent,
        title: 'Eventi'
    },
    {
        path: 'events/crea',
        component: CreateEventComponent,
        title: 'Crea Evento'
    },
    {
        path: 'events/:id',
        component: EventDetailComponent,
        title: 'Dettaglio Evento'
    },
    {
        path: 'login',
        component: LoginComponent,
        title: 'Login'
    },
    {
        path: '',
        redirectTo: '/events',
        pathMatch: 'full'
    }
];