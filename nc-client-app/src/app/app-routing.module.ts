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
import { ChooseMagazineDialogComponent } from './components/choose-magazine-dialog/choose-magazine-dialog.component';
import { ScientificWorkInfoDialogComponent } from './components/scientific-work-info-dialog/scientific-work-info-dialog.component';
import { CoauthorDialogComponent } from './components/coauthor-dialog/coauthor-dialog.component';
import { UploadFileDialogComponent } from './components/upload-file-dialog/upload-file-dialog.component';
import { CheckScientificWorkDialogComponent } from './components/check-scientific-work-dialog/check-scientific-work-dialog.component';
import { CheckPdfDialogComponent } from './components/check-pdf-dialog/check-pdf-dialog.component';
import { SetCorrectionTimeDialogComponent } from './components/set-correction-time-dialog/set-correction-time-dialog.component';
import { MyScientificWorksComponent } from './components/my-scientific-works/my-scientific-works.component';
import { ChangeFormatDialogComponent } from './components/change-format-dialog/change-format-dialog.component';
import { SelectReviewersDialogComponent } from './components/select-reviewers-dialog/select-reviewers-dialog.component';
import { SetReviewTimeDialogComponent } from './components/set-review-time-dialog/set-review-time-dialog.component';
import { ActiveReviewsComponent } from './components/active-reviews/active-reviews.component';
import { ReviewDialogComponent } from './components/review-dialog/review-dialog.component';
import { ReviewResultsDialogComponent } from './components/review-results-dialog/review-results-dialog.component';
import { EditorDecisionDialogComponent } from './components/editor-decision-dialog/editor-decision-dialog.component';
import { SetChangingTimeDialogComponent } from './components/set-changing-time-dialog/set-changing-time-dialog.component';
import { ReviewerCommentDialogComponent } from './components/reviewer-comment-dialog/reviewer-comment-dialog.component';
import { ChangePdfDialogComponent } from './components/change-pdf-dialog/change-pdf-dialog.component';
import { EditorFinalDecisionDialogComponent } from './components/editor-final-decision-dialog/editor-final-decision-dialog.component';
import { SetChangingTimeAgainDialogComponent } from './components/set-changing-time-again-dialog/set-changing-time-again-dialog.component';
import { NewReviewersDialogComponent } from './components/new-reviewers-dialog/new-reviewers-dialog.component';

const routes: Routes = [
  { path: '', component: HomepageComponent },
  { path: 'verify_account', component: VerifyComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'magazine', component: MagazineHomepageComponent },
  { path: 'magazine/:id', component: MagazineHomepageComponent },
  { path: 'magazine_list', component: MagazineListComponent },
  { path: 'magazine_list/:id', component: MagazineListComponent },
  { path: 'edition/:id', component: EditionComponent },
  { path: 'edition/:id/:orderId', component: EditionComponent },
  { path: 'my_scientific_works', component: MyScientificWorksComponent },
  { path: 'scientific_work/:id', component: ScientificWorkComponent },
  { path: 'scientific_work/:id/:orderId', component: ScientificWorkComponent },
  { path: 'active_reviews', component: ActiveReviewsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const RoutingComponents = [HomepageComponent,
  RegistrationDialogComponent, LoginComponent, VerifyComponent, ProfileComponent, ProfileDialogComponent, MagazineComponent,
  MagazineHomepageComponent, MagazineERComponent, ProfileMagazineDialogComponent, MagazineListComponent, EditionComponent,
  ScientificWorkComponent, ChooseMagazineDialogComponent, ScientificWorkInfoDialogComponent, CoauthorDialogComponent,
  UploadFileDialogComponent, CheckScientificWorkDialogComponent, CheckPdfDialogComponent, SetCorrectionTimeDialogComponent,
  MyScientificWorksComponent, ChangeFormatDialogComponent, SelectReviewersDialogComponent, SetReviewTimeDialogComponent,
  ActiveReviewsComponent, ReviewDialogComponent, ReviewResultsDialogComponent, EditorDecisionDialogComponent, SetChangingTimeDialogComponent,
  ReviewerCommentDialogComponent, ChangePdfDialogComponent, EditorFinalDecisionDialogComponent, SetChangingTimeAgainDialogComponent, NewReviewersDialogComponent];
