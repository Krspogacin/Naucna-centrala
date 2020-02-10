import { Component, OnInit, Inject } from '@angular/core';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-set-review-time-dialog',
  templateUrl: './set-review-time-dialog.component.html',
  styleUrls: ['./set-review-time-dialog.component.css']
})
export class SetReviewTimeDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  username = null;

  constructor(private scientificWorkService: ScientificWorkService,
    private authenticationService: AuthenticationService,
    private util: Util,
    private dialogRef: MatDialogRef<SetReviewTimeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public taskId: any) {

    console.log(taskId);
    this.username = authenticationService.getUsername();
    console.log(this.username);

    this.scientificWorkService.reviewrsSetTimeFormFields(taskId, this.username).subscribe(
      (data: any) => {
        console.log(data);
        this.formFieldsDto = data;
        this.formFields = data.formFields;
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      });
  }

  ngOnInit() {
  }

  onSubmit(value) {
    let o = new Array();
    for (var property in value) {
      o.push({ fieldId: property, fieldValue: value[property] });
    }

    console.log(o);

    this.scientificWorkService.reviewersSetTime(o, this.formFieldsDto.taskId).subscribe(
      (data) => {
        this.dialogRef.close();
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      });
  }

}
