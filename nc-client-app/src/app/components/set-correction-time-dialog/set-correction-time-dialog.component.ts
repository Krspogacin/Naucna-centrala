import { Component, OnInit, Inject } from '@angular/core';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-set-correction-time-dialog',
  templateUrl: './set-correction-time-dialog.component.html',
  styleUrls: ['./set-correction-time-dialog.component.css']
})
export class SetCorrectionTimeDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  username = null;

  constructor(private scientificWorkService: ScientificWorkService,
    private authenticationService: AuthenticationService,
    private util: Util,
    private dialogRef: MatDialogRef<SetCorrectionTimeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public processInstanceId: any) {

    console.log(processInstanceId);
    this.username = authenticationService.getUsername();
    console.log(this.username);

    this.scientificWorkService.setTimeFormFields(processInstanceId, this.username).subscribe(
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

    this.scientificWorkService.setTime(o, this.formFieldsDto.taskId).subscribe(
      (data) => {
        this.dialogRef.close();
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      });
  }

}
