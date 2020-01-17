import { Component, OnInit, Inject } from '@angular/core';
import { RepositoryService } from 'src/app/services/repository/repository.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Util } from 'src/app/utils';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-profile-dialog',
  templateUrl: './profile-dialog.component.html',
  styleUrls: ['./profile-dialog.component.css']
})
export class ProfileDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;

  constructor(private repositoryService: RepositoryService,
    private dialogRef: MatDialogRef<ProfileDialogComponent>,
    private util: Util,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public taskId: any) {

    this.repositoryService.getReviewerFormFields(taskId).subscribe(
      (data: any) => {
        console.log(data);
        this.formFieldsDto = data;
        this.formFields = data.formFields;
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      }
    )

  }

  ngOnInit() {
  }

  onSubmit(value) {
    if (value['potvrdi'] == "") {
      value['potvrdi'] = false;
    }
    let checkReviewerDto = { taskId: this.taskId, flag: value['potvrdi'] };
    console.log(checkReviewerDto);
    this.userService.checkReviewer(checkReviewerDto).subscribe(
      (data) => {
        console.log(data);
        this.dialogRef.close(value['potvrdi']);
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      }
    )

  }

}
