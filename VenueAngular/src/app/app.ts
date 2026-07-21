import { Component, inject } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent {
  private authService = inject(AuthService);
  isLogged = false;

  constructor() {
    this.isLogged = this.authService.isLoggedIn();
    this.authService.getAuthState().subscribe(v => this.isLogged = v);
  }

  logout(): void {
    this.authService.logout();
    window.location.href = '/';
  }
}