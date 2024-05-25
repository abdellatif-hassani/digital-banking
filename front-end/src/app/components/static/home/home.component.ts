import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
    services = [
      { title: 'Online Banking', text: 'Manage your accounts and transactions online 24/7.' },
      { title: 'Mobile Banking', text: 'Bank on the go with our mobile app available on all devices.' },
      { title: 'Loan Services', text: 'Get quick and easy loans with competitive interest rates.' }
    ];

    testimonials = [
      { text: 'Digital Bank has transformed the way I manage my finances. Highly recommend!', author: 'Abdellatif Hassani' },
      { text: 'Excellent customer service and easy-to-use mobile app.', author: 'Yassine Bono' },
      { text: 'I love the convenience of online banking with Digital Bank.', author: 'Achraf Hakimi' }
    ];
}
