import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  signUpRequest = { name: '', email: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.signUp(this.signUpRequest)
      .subscribe(response => {
        console.log('Customer signed up successfully', response);
        this.router.navigate(['/sign-in']);

      }, error => {
        console.error('Error signing up customer', error);
        this.router.navigate(['/sign-in']);
      });
  }
}
