import { Component } from '@angular/core';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {
  contactData = {
    name: '',
    email: '',
    message: ''
  };

  onSubmit() {
    console.log('Contact Form Data:', this.contactData);
    // Add your form submission logic here, e.g., send data to a backend server
  }
}
