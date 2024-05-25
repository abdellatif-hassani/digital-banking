import { Component, OnInit } from '@angular/core';
import { BankAccountsService } from '../../../services/bank-accounts.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit {
  accounts: any[] = [];
  transferData = {
    accountSource: null,
    accountDestination: null,
    amount: 0,
    description: ''
  };
  errorMessage: string = '';

  constructor(private bankAccountsService: BankAccountsService, 
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.errorMessage = params['error'] || '';
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


  transfer(): void {
    console.log('Transfer data:', this.transferData);
    this.bankAccountsService.transfer(this.transferData).subscribe(
      response => {
        console.log('Transfer successful:', response);
        this.router.navigate(['/list-accounts'], { queryParams: { message: 'Transfer successful' } });
      },
      error => {
        console.error('Error during transfer:', error);
        this.router.navigate(['/transfer'], { queryParams: { error: 'Error during transfer: ' + error.error } });
      }
    );
  }
}
