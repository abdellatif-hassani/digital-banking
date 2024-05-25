import { Component, OnInit } from '@angular/core';
import { BankAccountsService } from '../../../services/bank-accounts.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-list-accounts',
  templateUrl: './list-accounts.component.html',
  styleUrls: ['./list-accounts.component.css']
})
export class ListAccountsComponent implements OnInit {
  accounts: any[] = [];
  message: string = '';

  constructor(private bankAccountsService: BankAccountsService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.message = params['message'] || '';
    });

    this.bankAccountsService.getCustomerAccounts().subscribe(
      data => {
        this.accounts = data;
      },
      error => {
        console.error('Error fetching accounts:', error);
      }
    );
  }


}
