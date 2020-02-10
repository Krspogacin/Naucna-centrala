import { Component, OnInit, Inject } from '@angular/core';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-scientific-work-info-dialog',
  templateUrl: './scientific-work-info-dialog.component.html',
  styleUrls: ['./scientific-work-info-dialog.component.css']
})
export class ScientificWorkInfoDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  private enumValues = [];

  constructor(private scientificWorkService: ScientificWorkService,
    private authenticationService: AuthenticationService,
    private util: Util,
    private dialogRef: MatDialogRef<ScientificWorkInfoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public username: any) {

    console.log(username);

    const processInstaceId = this.authenticationService.getProcessInstanceId();
    if (!processInstaceId) {
      this.util.showSnackBar("Doslo je do greske prilikom kreiranja naucnog rada");
      return;
    }
    console.log(processInstaceId);

    this.scientificWorkService.getInfoFormFields(processInstaceId, username).subscribe(
      (data: any) => {
        console.log(data);
        this.formFieldsDto = data;
        this.formFields = data.formFields;
        this.formFields.forEach((field) => {

          if (field.type.name == 'enum') {
            this.enumValues = Object.keys(field.type.values);
            console.log(this.enumValues)
          }
        });
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      }
    )
  }

  ngOnInit() {
  }

  onSubmit(value) {
    let o = new Array();
    for (var property in value) {
      o.push({ fieldId: property, fieldValue: value[property] });
    }

    console.log(o);
    this.scientificWorkService.saveScientificWork(o, this.formFieldsDto.taskId).subscribe(
      (data) => {
        console.log(data);
        this.dialogRef.close();
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      }
    );
  }

}
