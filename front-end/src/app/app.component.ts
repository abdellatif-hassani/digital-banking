import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  constructor(private authService: AuthService) {}

  ngOnInit() {
    // Any initialization logic can go here
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }
}
