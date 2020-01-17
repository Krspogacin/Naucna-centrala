import { Component, OnInit } from '@angular/core';
import { Util } from 'src/app/utils';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { RepositoryService } from 'src/app/services/repository/repository.service';
import { MatDialog } from '@angular/material';
import { ProfileDialogComponent } from '../profile-dialog/profile-dialog.component';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { ProfileMagazineDialogComponent } from '../profile-magazine-dialog/profile-magazine-dialog.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  reviewerTasks: any;
  magazineTasks: any;
  role: any = null;

  constructor(private util: Util,
    private authenticationService: AuthenticationService,
    private repositoryService: RepositoryService,
    private magazineService: MagazineService,
    private dialog: MatDialog) { }

  ngOnInit() {
    let roles = this.authenticationService.getRoles();
    for (var role in roles) {
      if (roles[role] == "ROLE_ADMINISTRATOR") {
        this.role = roles[role];
      }
    }
    this.repositoryService.getReviewerTasks().subscribe(
      (data) => {
        console.log(data);
        this.reviewerTasks = data;
      },
      (error) => {
        this.util.showSnackBar("Error while getting tasks");
      });

    this.magazineService.getMagazineTasks().subscribe(
      (data) => {
        console.log(data);
        this.magazineTasks = data;
      },
      (error) => {
        this.util.showSnackBar("Error while getting tasks");
      });
  }

  checkReviewer(task) {
    const dialogRef = this.dialog.open(ProfileDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '40%'
      });
    dialogRef.afterClosed().subscribe(
      (data: any) => {
        task.flag = data;
        this.util.showSnackBar('You have checked reviewer succesfuly!');
      },
      (error) => {
        this.util.showSnackBar('Error while checking reviewer!');
      });
  }

  checkMagazine(task) {
    const dialogRef = this.dialog.open(ProfileMagazineDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '40%'
      });
    dialogRef.afterClosed().subscribe(
      (data: any) => {
        task.flag = data;
        this.util.showSnackBar('You have checked magazine succesfuly!');
      },
      (error) => {
        this.util.showSnackBar('Error while checking magazine!');
      });
  }

}
