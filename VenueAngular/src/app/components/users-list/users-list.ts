import { Component, OnInit, PLATFORM_ID, inject, ChangeDetectorRef } from '@angular/core'; 
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserDTO } from '../../models/UserDTO';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './users-list.html',
  styleUrl: './users-list.css'
})
export class UsersListComponent implements OnInit {
  users: UserDTO[] = [];
  newUser: UserDTO = { email: '', password: '' };
  isLoading = false;
  isDeletingId: number | null = null;
  currentUserId: number | null = null;
  currentUserEmail: string | null = null;
  errorMessage: string | null = null;

  private userService = inject(UserService);
  private authService = inject(AuthService);
  private platformId = inject(PLATFORM_ID);
  private isBrowser = isPlatformBrowser(this.platformId);
  private cdr = inject(ChangeDetectorRef); 

  ngOnInit(): void {
    this.currentUserId = this.authService.getCurrentUserId();
    this.currentUserEmail = this.authService.getCurrentUserEmail();

    if (this.isBrowser) {
      this.loadUsers();
    }
  }

  loadUsers(): void {
    if (!this.isBrowser) return;

    this.errorMessage = null;
    this.userService.getUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.cdr.markForCheck();
      },
      error: (err) => {
        const status = err?.status ?? err?.statusCode;
        this.errorMessage = status === 403 
          ? 'Accesso negato: effettua il login con un account autorizzato.' 
          : 'Non è stato possibile caricare gli utenti.';
        this.cdr.markForCheck();
        this.notify(this.errorMessage);
      }
    });
  }

  onCreate(): void {
    if (!this.newUser.email || !this.newUser.password) {
      this.notify('Compila email e password.');
      return;
    }

    this.isLoading = true;
    this.errorMessage = null;
    this.userService.createUser(this.newUser).subscribe({
      next: () => {
        this.loadUsers();
        this.newUser = { email: '', password: '' };
      },
      error: (err) => {
        const status = err?.status ?? err?.statusCode;
        this.errorMessage = status === 403 
          ? 'Accesso negato: non hai i permessi per creare un utente.' 
          : 'Errore durante la creazione dell\'utente.';
        this.cdr.markForCheck();
        this.notify(this.errorMessage);
      },
      complete: () => {
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onDelete(user: UserDTO): void {
    if (this.isCurrentUser(user)) {
      this.notify('Non puoi eliminare l\'utente con cui sei loggato.');
      return;
    }

    if (user.id == null) {
      this.notify('Impossibile eliminare utente senza identificativo.');
      return;
    }

    if (this.isBrowser && !confirm(`Eliminare l'utente ${user.email}?`)) return;

    this.isDeletingId = user.id;
    this.userService.deleteUser(user.id).subscribe({
      next: () => {
        this.loadUsers();
      },
      error: (err) => {
        const status = err?.status ?? err?.statusCode;
        this.errorMessage = status === 403 
          ? 'Accesso negato: non hai i permessi per eliminare questo utente.' 
          : 'Errore durante l\'eliminazione dell\'utente.';
        this.notify(this.errorMessage);
        this.isDeletingId = null;
        this.cdr.markForCheck();
      },
      complete: () => {
        this.isDeletingId = null;
        this.cdr.markForCheck();
      }
    });
  }

  isCurrentUser(user: UserDTO): boolean {
    if (user.id != null && this.currentUserId != null) {
      return user.id === this.currentUserId;
    }
    return this.currentUserEmail != null && user.email === this.currentUserEmail;
  }

  private notify(message: string): void {
    if (this.isBrowser) {
      alert(message);
    } else {
      console.error(message);
    }
  }
}