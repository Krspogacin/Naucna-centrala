import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Util } from 'src/app/utils';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';

@Component({
  selector: 'app-reviewer-comment-dialog',
  templateUrl: './reviewer-comment-dialog.component.html',
  styleUrls: ['./reviewer-comment-dialog.component.css']
})
export class ReviewerCommentDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  private username = null;

  constructor(private dialogRef: MatDialogRef<ReviewerCommentDialogComponent>,
    private util: Util,
    private authenticationService: AuthenticationService,
    private scientificWorkService: ScientificWorkService,
    @Inject(MAT_DIALOG_DATA) public taskId: any) {

    console.log(taskId);
    this.username = authenticationService.getUsername();
    console.log(this.username);

    this.scientificWorkService.reviewerCommentFormFields(taskId, this.username).subscribe(
      (data: any) => {
        console.log(data);
        this.formFieldsDto = data;
        this.formFields = data.formFields;
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      }
    );


  }

  ngOnInit() {
  }

  onSubmit(value) {
    let o = new Array();
    for (var property in value) {
      o.push({ fieldId: property, fieldValue: value[property] });
    }

    console.log(o);

    this.scientificWorkService.reviewerComment(o, this.taskId).subscribe(
      (data) => {
        this.dialogRef.close();
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      });
  }

}
