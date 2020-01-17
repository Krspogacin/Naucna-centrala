import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { RegistrationDialogComponent } from './components/registration-dialog/registration-dialog.component';
import { VerifyComponent } from './components/verify/verify.component'
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ProfileDialogComponent } from './components/profile-dialog/profile-dialog.component';
import { MagazineComponent } from './components/magazine/magazine.component';
import { MagazineHomepageComponent } from './components/magazine-homepage/magazine-homepage.component';
import { MagazineERComponent } from './components/magazine-er/magazine-er.component';
import { ProfileMagazineDialogComponent } from './components/profile-magazine-dialog/profile-magazine-dialog.component';

const routes: Routes = [
  { path: '', component: HomepageComponent },
  { path: 'verify_account', component: VerifyComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'magazine', component: MagazineHomepageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const RoutingComponents = [HomepageComponent,
  RegistrationDialogComponent, LoginComponent, VerifyComponent, ProfileComponent, ProfileDialogComponent, MagazineComponent,
  MagazineHomepageComponent, MagazineERComponent, ProfileMagazineDialogComponent];
