import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8081/auth';

  constructor(private http: HttpClient) {}

  signUp(signUpRequest: { name: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, signUpRequest);
  }

  signIn(authRequest: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/signin`, authRequest);
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  signOut(): void {
    localStorage.removeItem('token');
  }
}
