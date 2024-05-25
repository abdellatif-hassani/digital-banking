import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = `http://localhost:8081/api/customers`;

  constructor(private http: HttpClient) {}

  private getToken() {
    return localStorage.getItem('token'); // Get token from local storage or wherever it's stored
  }

  getUserDetails(): Observable<any> {
    const token = this.getToken();
    const userId = this.getCustomerIdFromToken(token!);
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(`${this.baseUrl}/${userId}/details`, { headers });
  }

  private getCustomerIdFromToken(token: string) {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.customerId;
  }



}
