import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { Observable, tap, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  
  private loggedIn = new BehaviorSubject<boolean>(false);

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('token');
      this.loggedIn.next(!!token);
      console.log('Stato iniziale token:', !!token);
    }
  }

  getAuthState(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  login(email: string, password: string): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, { email, password }).pipe(
      tap(response => {
        if (isPlatformBrowser(this.platformId) && response && response.token) {
          localStorage.setItem('token', response.token);
          this.loggedIn.next(true);
        }
      })
    );
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('token');
      this.loggedIn.next(false); 
    }
  }

  isLoggedIn(): boolean {
    return this.loggedIn.value;
  }

  getCurrentUserEmail(): string | null {
    const payload = this.getTokenPayload();
    if (!payload) {
      return null;
    }

    if (typeof payload.email === 'string') {
      return payload.email;
    }

    if (typeof payload.sub === 'string') {
      return payload.sub;
    }

    return null;
  }

  getCurrentUserId(): number | null {
    const payload = this.getTokenPayload();
    if (!payload) {
      return null;
    }

    const possibleIds = [payload.sub, payload.id, payload.userId];

    for (const value of possibleIds) {
      if (typeof value === 'number') {
        return value;
      }
      if (typeof value === 'string' && !isNaN(Number(value))) {
        return Number(value);
      }
    }

    return null;
  }

  private getTokenPayload(): any | null {
    if (!isPlatformBrowser(this.platformId)) {
      return null;
    }

    const token = localStorage.getItem('token');
    if (!token) {
      return null;
    }

    const parts = token.split('.');
    if (parts.length < 2) {
      return null;
    }

    try {
      const payloadJson = atob(parts[1].replace(/-/g, '+').replace(/_/g, '/'));
      return JSON.parse(payloadJson);
    } catch {
      return null;
    }
  }
}