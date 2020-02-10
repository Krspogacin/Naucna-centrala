import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Util } from 'src/app/utils';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';

@Component({
  selector: 'app-check-pdf-dialog',
  templateUrl: './check-pdf-dialog.component.html',
  styleUrls: ['./check-pdf-dialog.component.css']
})
export class CheckPdfDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  private username = null;

  constructor(private dialogRef: MatDialogRef<CheckPdfDialogComponent>,
    private util: Util,
    private authenticationService: AuthenticationService,
    private scientificWorkService: ScientificWorkService,
    @Inject(MAT_DIALOG_DATA) public taskId: any) {

    console.log(taskId);
    this.username = authenticationService.getUsername();
    console.log(this.username);

    this.scientificWorkService.checkPdfFormFields(taskId, this.username).subscribe(
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
    console.log(value)
    if (value['formatiran'] == "") {
      value['formatiran'] = false;
    }

    let o = new Array();
    for (var property in value) {
      o.push({ fieldId: property, fieldValue: value[property] });
    }

    console.log(o);

    this.scientificWorkService.checkPdf(o, this.taskId).subscribe(
      (data) => {
        let object = { flag: value['formatiran'], processInstanceId: this.formFieldsDto.processInstanceId }
        console.log(object);
        this.dialogRef.close(object);
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      });
  }

}
