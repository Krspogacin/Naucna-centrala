import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';
import { Util } from 'src/app/utils';
import { MatDialog } from '@angular/material';
import { ReviewDialogComponent } from '../review-dialog/review-dialog.component';
import { UploadService } from 'src/app/services/upload/upload.service';

@Component({
  selector: 'app-active-reviews',
  templateUrl: './active-reviews.component.html',
  styleUrls: ['./active-reviews.component.css']
})
export class ActiveReviewsComponent implements OnInit {

  tasks: any;
  username = null;

  constructor(private authenticationService: AuthenticationService,
    private scientificWorkService: ScientificWorkService,
    private uploadService: UploadService,
    private util: Util,
    private dialog: MatDialog) {

    this.username = this.authenticationService.getUsername();
    this.scientificWorkService.getReviewTasks(this.username).subscribe(
      (data) => {
        console.log(data);
        this.tasks = data;
      },
      (error) => {
        this.util.showSnackBar("Error while getting tasks");
      });
  }

  ngOnInit() {
  }

  getScientificWork(task) {
    this.uploadService.download(task.id).subscribe(
      (data: any) => {
        const url = window.URL.createObjectURL(data);
        window.open(url);
      },
      (error) => {
        console.log('Greska: ' + error);;
      });
  }

  review(task) {
    const dialogRef = this.dialog.open(ReviewDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      () => {
        task.flag = true;
        this.util.showSnackBar('Successfully changed scientific work!');
      },
      () => {
        this.util.showSnackBar('Error while submit your review!');
      });
  }

}
