import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<string> {
    const params = new HttpParams()
      .set('email', email)
      .set('password', password);

    return this.http.get(`${this.apiUrl}/login`, { params, responseType: 'text' });
  }
}