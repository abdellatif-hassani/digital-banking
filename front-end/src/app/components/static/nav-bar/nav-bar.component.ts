import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {

  navLinks = [
    { label: 'Home', link: '/home', active: true },
    { 
      label: 'Services', 
      active: false,
      dropdown: true,
      dropdownLinks: [
        { label: 'My Accounts', link: '/list-accounts' },
        { label: 'Open bank Account', link: '/accounts/create-account' },
        { label: 'Transfer', link: '/transfer' }
      ]
    },
    { label: 'Contact', link: '/contact', active: false }
  ];

  constructor(private authService: AuthService, private router: Router) {}

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  signOut(): void {
    this.authService.signOut();
    this.router.navigate(['/sign-in']);
  }
  

}
