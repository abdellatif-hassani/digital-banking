import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {
  authRequest = { email: '', password: '' };
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.signIn(this.authRequest)
      .subscribe(response => {
        console.log('Sign-in successful', response);
        localStorage.setItem('token', response.jwt); // Store JWT token
        this.router.navigate(['/']);
      }, error => {
        console.error('Error signing in', error);
        if (error.status === 401) {
          this.errorMessage = 'Invalid email or password';
        } else {
          this.errorMessage = 'An error occurred. Please try again.';
        }
      });
  }
}
