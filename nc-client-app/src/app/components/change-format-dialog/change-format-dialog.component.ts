import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Util } from 'src/app/utils';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';

@Component({
  selector: 'app-change-format-dialog',
  templateUrl: './change-format-dialog.component.html',
  styleUrls: ['./change-format-dialog.component.css']
})
export class ChangeFormatDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  private username = null;

  constructor(private dialogRef: MatDialogRef<ChangeFormatDialogComponent>,
    private util: Util,
    private authenticationService: AuthenticationService,
    private scientificWorkService: ScientificWorkService,
    @Inject(MAT_DIALOG_DATA) public taskId: any) {

    console.log(taskId);
    this.username = authenticationService.getUsername();
    console.log(this.username);

    this.scientificWorkService.changeFormatFormFields(taskId, this.username).subscribe(
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

    this.scientificWorkService.changeFormat(o, this.taskId).subscribe(
      (data) => {
        this.dialogRef.close();
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      });
  }


}
