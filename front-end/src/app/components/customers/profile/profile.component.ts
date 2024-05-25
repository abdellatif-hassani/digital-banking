import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: any;
  accounts: any[] = [];
  errorMessage: string = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getUserDetails().subscribe(
      data => {
        this.user = data.customer;
        this.accounts = data.bankAccounts;
      },
      error => {
        console.error('Error fetching user details:', error);
        this.errorMessage = 'Error fetching user details.';
      }
    );
  }
}
