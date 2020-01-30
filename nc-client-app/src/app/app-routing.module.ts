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
import { MagazineListComponent } from './components/magazine-list/magazine-list.component';
import { EditionComponent } from './components/edition/edition.component';
import { ScientificWorkComponent } from './components/scientific-work/scientific-work.component';

const routes: Routes = [
  { path: '', component: HomepageComponent },
  { path: 'verify_account', component: VerifyComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'magazine', component: MagazineHomepageComponent },
  { path: 'magazine/:id', component: MagazineHomepageComponent },
  { path: 'magazine_list', component: MagazineListComponent },
  { path: 'edition/:id', component: EditionComponent },
  { path: 'edition/:id/:orderId', component: EditionComponent },
  { path: 'scientific_work/:id', component: ScientificWorkComponent },
  { path: 'scientific_work/:id/:orderId', component: ScientificWorkComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const RoutingComponents = [HomepageComponent,
  RegistrationDialogComponent, LoginComponent, VerifyComponent, ProfileComponent, ProfileDialogComponent, MagazineComponent,
  MagazineHomepageComponent, MagazineERComponent, ProfileMagazineDialogComponent, MagazineListComponent, EditionComponent,
  ScientificWorkComponent];
