import { Routes } from '@angular/router';
import { EventListComponent } from './components/event-list/event-list';
import { LoginComponent } from './components/login/login';
import { EventDetailComponent } from './components/event-detail/event-detail';

export const routes: Routes = [
    {
        path: 'events',
        component: EventListComponent,
        title: 'Eventi'
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
    },
{
    path: 'events/:id', 
    component: EventDetailComponent,
    title: 'Dettaglio Evento'
}
];