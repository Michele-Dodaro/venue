import { Component } from '@angular/core';
import { EventListComponent } from './components/event-list/event-list'; 

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [EventListComponent], 
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent {
  title = 'venue-frontend';
}