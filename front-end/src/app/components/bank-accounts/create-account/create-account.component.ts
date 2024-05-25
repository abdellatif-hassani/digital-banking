import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { BankAccountsService } from '../../../services/bank-accounts.service';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent {
  accountType: string = 'current';
  account: any = {
    balance: 0,
    overDraft: 0,
    interestRate: 0
  };

  constructor(private bankAccountService: BankAccountsService, private router: Router) {}

  createAccount() {
    const accountData = {
      ...this.account,
      type: this.accountType
    };
    this.bankAccountService.createAccount(accountData).subscribe(response => {
      console.log('Account created successfully', response);
      this.router.navigate(['/list-accounts']);
    }, error => {
      console.error('Error creating account', error);
    });
  }

 
}
