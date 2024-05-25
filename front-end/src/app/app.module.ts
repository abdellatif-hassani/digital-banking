import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './components/static/nav-bar/nav-bar.component';
import { HomeComponent } from './components/static/home/home.component';
import { SignUpComponent } from './components/accounts/sign-up/sign-up.component';
import { SignInComponent } from './components/accounts/sign-in/sign-in.component';
import { FooterComponent } from './components/static/footer/footer.component';
import { ContactComponent } from './components/static/contact/contact.component';
import { CreateAccountComponent } from './components/bank-accounts/create-account/create-account.component';
import { ListAccountsComponent } from './components/bank-accounts/list-accounts/list-accounts.component';
import { NotFoundComponent } from './components/static/not-found/not-found.component';
import { TransferComponent } from './components/bank-accounts/transfer/transfer.component';
import { ProfileComponent } from './components/customers/profile/profile.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    HomeComponent,
    SignUpComponent,
    SignInComponent,
    FooterComponent,
    ContactComponent,
    CreateAccountComponent,
    ListAccountsComponent,
    NotFoundComponent,
    TransferComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
