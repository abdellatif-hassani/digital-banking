import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BankAccountsService {

  accountType: string = 'current';
  account: any = {
    balance: 0,
    overDraft: 0,
    interestRate: 0
  };
  private baseUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient, private router: Router) {}

  createAccount(accountData: any): Observable<any> {
    const token = this.getToken(); 
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post(`${this.baseUrl}/accounts/createAccount`, accountData, { headers });
  }

  getCustomerAccounts(): Observable<any> {
    const token = this.getToken();
    const customerId = this.getCustomerIdFromToken(token!);
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(`${this.baseUrl}/customers/${customerId}/accounts`, { headers });
  }

  transfer(transferData: any): Observable<any> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post(`${this.baseUrl}/accounts/transfer`, transferData, { headers });
  }


  private getToken() {
    return localStorage.getItem('token'); // Get token from local storage or wherever it's stored
  }

  private getCustomerIdFromToken(token: string) {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.customerId;
  }
  

}
