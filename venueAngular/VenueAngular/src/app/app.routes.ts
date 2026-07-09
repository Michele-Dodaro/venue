import { Routes } from '@angular/router';
import { EventListComponent } from './components/event-list/event-list';
import { LoginComponent } from './components/login/login';

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
    }
];