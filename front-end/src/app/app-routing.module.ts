import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/static/home/home.component';
import { SignUpComponent } from './components/accounts/sign-up/sign-up.component';
import { SignInComponent } from './components/accounts/sign-in/sign-in.component';
import { ContactComponent } from './components/static/contact/contact.component';
import { CreateAccountComponent } from './components/bank-accounts/create-account/create-account.component';
import { ListAccountsComponent } from './components/bank-accounts/list-accounts/list-accounts.component'; 
import { ProfileComponent } from './components/customers/profile/profile.component';

import { TransferComponent } from './components/bank-accounts/transfer/transfer.component';
import { NotFoundComponent } from './components/static/not-found/not-found.component';

import { AuthGuard } from './guards/auth.guard';
import { AuthRedirectGuard } from './guards/auth-redirect.guard';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'sign-up', component: SignUpComponent, canActivate: [AuthRedirectGuard] },
  { path: 'sign-in', component: SignInComponent, canActivate: [AuthRedirectGuard] },
  { path: 'accounts/create-account', component: CreateAccountComponent, canActivate: [AuthGuard] },
  { path: 'list-accounts', component: ListAccountsComponent, canActivate: [AuthGuard] },
    { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] }, // Add ProfileComponent route
  { path: 'transfer', component: TransferComponent, canActivate: [AuthGuard] }, // Add TransferComponent route
  { path: '**', component: NotFoundComponent } // Wildcard route for handling 404

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
