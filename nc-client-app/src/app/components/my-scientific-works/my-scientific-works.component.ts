import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';
import { Util } from 'src/app/utils';
import { MatDialog } from '@angular/material';
import { ChangeFormatDialogComponent } from '../change-format-dialog/change-format-dialog.component';
import { UploadFileDialogComponent } from '../upload-file-dialog/upload-file-dialog.component';
import { ReviewerCommentDialogComponent } from '../reviewer-comment-dialog/reviewer-comment-dialog.component';
import { ChangePdfDialogComponent } from '../change-pdf-dialog/change-pdf-dialog.component';

@Component({
  selector: 'app-my-scientific-works',
  templateUrl: './my-scientific-works.component.html',
  styleUrls: ['./my-scientific-works.component.css']
})
export class MyScientificWorksComponent implements OnInit {

  tasks = [];
  tasks2 = [];
  tasks3 = [];
  username = null;

  constructor(private authenticationService: AuthenticationService,
    private scientificWorkService: ScientificWorkService,
    private util: Util,
    private dialog: MatDialog) {

    this.username = this.authenticationService.getUsername();
    this.scientificWorkService.getChangeFormatTasks(this.username).subscribe(
      (data: any) => {
        console.log(data);
        this.tasks = data;
      },
      (error) => {
        this.util.showSnackBar("Error while getting tasks");
      });

    this.scientificWorkService.getReviewerCommentTasks(this.username).subscribe(
      (data: any) => {
        console.log(data);
        this.tasks2 = data;
      },
      (error) => {
        this.util.showSnackBar("Error while getting tasks");
      });

    this.scientificWorkService.getChangePdfTasks(this.username).subscribe(
      (data: any) => {
        console.log(data);
        this.tasks3 = data;
      },
      (error) => {
        this.util.showSnackBar("Error while getting tasks");
      });

  }

  ngOnInit() {
  }

  changeFormat(task) {
    const dialogRef = this.dialog.open(ChangeFormatDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      () => {
        const dialogRef = this.dialog.open(UploadFileDialogComponent,
          {
            data: this.username,
            disableClose: true,
            autoFocus: true,
            width: '50%'
          });
        dialogRef.afterClosed().subscribe(
          () => {
            this.util.showSnackBar('Successfully changed scientific work!');
            task.flag = true;
          });
      });
  }

  reviewerComment(task) {
    const dialogRef = this.dialog.open(ReviewerCommentDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      () => {
        this.scientificWorkService.getChangePdfTasks(this.username).subscribe(
          (data: any) => {
            console.log(data);
            this.tasks3 = data;
          },
          (error) => {
            this.util.showSnackBar("Error while getting tasks");
          });
        task.flag = true;
        this.util.showSnackBar('Succesfully reply to comment!');
      }
    );
  }

  changeScientificWork(task) {
    const dialogRef = this.dialog.open(ChangePdfDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      () => {
        this.util.showSnackBar('Successfully changed scientific work!');
        task.flag = true;
      });
  }
}
