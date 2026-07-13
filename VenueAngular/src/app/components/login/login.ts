import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {
  email = '';
  password = '';
  isLoading = false;

  private authService = inject(AuthService);
  private router = inject(Router);

onLogin() {
  if (!this.email || !this.password) return;

  this.isLoading = true;
  this.authService.login(this.email, this.password).subscribe({
    next: () => {
      this.router.navigate(['/events']); 
    },
    error: (err) => {
      console.error('Login failed', err);
      alert('Credenziali non valide!');
      this.isLoading = false;
    },
    complete: () => {
      this.isLoading = false;
    }
  });
}}