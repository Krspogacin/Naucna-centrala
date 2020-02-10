import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Util } from 'src/app/utils';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
  selector: 'app-check-scientific-work-dialog',
  templateUrl: './check-scientific-work-dialog.component.html',
  styleUrls: ['./check-scientific-work-dialog.component.css']
})
export class CheckScientificWorkDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  private username = null;

  constructor(private dialogRef: MatDialogRef<CheckScientificWorkDialogComponent>,
    private util: Util,
    private authenticationService: AuthenticationService,
    private scientificWorkService: ScientificWorkService,
    @Inject(MAT_DIALOG_DATA) public taskId: any) {

    console.log(taskId);
    this.username = authenticationService.getUsername();
    console.log(this.username);

    this.scientificWorkService.checkScientificWorkFormFields(taskId, this.username).subscribe(
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
    if (value['tematski_prihvatljiv'] == "") {
      value['tematski_prihvatljiv'] = false;
    }

    let o = new Array();
    for (var property in value) {
      o.push({ fieldId: property, fieldValue: value[property] });
    }

    console.log(o);

    this.scientificWorkService.checkScientificWork(o, this.taskId).subscribe(
      (data) => {
        this.dialogRef.close(value['tematski_prihvatljiv']);
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      }
    )

  }

}
