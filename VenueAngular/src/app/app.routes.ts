import { Routes } from '@angular/router';
import { EventListComponent } from './components/event-list/event-list';
import { LoginComponent } from './components/login/login';
import { EventDetailComponent } from './components/event-detail/event-detail';
import { CreateEventComponent } from './components/event-creation/event-creation';
import { ModifyEventComponent } from './components/modify-event/modify-event';
import { AddButtonComponent } from './components/add-button/add-button';
import { CreateMenuCategoryComponent } from './components/menu-category-creation/menu-category-creation';
import { MenuCategoryListComponent } from './components/menu-category-list/menu-category-list';
import { UsersListComponent } from './components/users-list/users-list';
import { UpdateMenuCategory } from './components/update-menu-category/update-menu-category';
import { CreateMenuItems } from './components/create-menu-items/create-menu-items';
import { ModifyMenuItemComponent } from './components/modify-menu-item/modify-menu-item';
import { CreateLayout} from './components/create-layout/create-layout';
import { SelectSeat } from './components/select-seat/select-seat';
import { Payment } from './components/payment/payment';
import { MenuPromotionsComponent } from './components/menu-promotions/menu-promotions';


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
        path: 'menu-category-list',
        component: MenuCategoryListComponent,
        title: 'Menu'
    },
    {
        path: 'events/:id',
        component: EventDetailComponent,
        title: 'Dettaglio Evento'
    },
    {
        path: 'events/:id/edit',
        component: ModifyEventComponent,
        title: 'Modifica Evento'
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
        path: 'add-button',
        component: AddButtonComponent,
        title: 'bottoni aggiunte'
    },
    {
        path: 'add-button/menu-category-creation',
        component: CreateMenuCategoryComponent,
        title: 'Crea Categoria Menu'
    },
    {
        path: 'users-list',
        component: UsersListComponent,
        title: 'Utenti'
    },

    {
        path: 'edit-category/:name',
        component: UpdateMenuCategory,
        title: 'Modifica Categoria Menu'
    },
    {
        path: 'categories/:categories/items/:items',
        component: MenuCategoryListComponent,
        title: 'Categoria Menu'
    },
    {
        path: 'categories/:categories/items',
        component: CreateMenuItems,
        title: 'Crea Elemento Menu'
    },
    {
        path: 'categories/:categoryName/items/:itemId/edit',
        component: ModifyMenuItemComponent,
        title: 'Modifica Piatto'
    },
    {
        path:'event-layout',
        component: CreateLayout,
        title: 'Crea Layout Evento'
    },
    {
        path: 'events/:id/seats',
        component: SelectSeat,
        title: 'Seleziona Posti'
    },
    {
        path: 'payment',
        component: Payment,
        title: 'Pagamento'
    },
    {
        path: 'menu-promotions',
        component: MenuPromotionsComponent,
        title: 'Gestione Offerte Menu'
    },
];