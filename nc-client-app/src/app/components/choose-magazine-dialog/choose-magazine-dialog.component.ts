import { Component, OnInit, Inject } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-choose-magazine-dialog',
  templateUrl: './choose-magazine-dialog.component.html',
  styleUrls: ['./choose-magazine-dialog.component.css']
})
export class ChooseMagazineDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  private enumValues = [];

  constructor(private scientificWorkService: ScientificWorkService,
    private authenticationService: AuthenticationService,
    private util: Util,
    private dialogRef: MatDialogRef<ChooseMagazineDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public username: any) {

    console.log(username);

    this.scientificWorkService.startProcess(this.username).subscribe(
      (data: any) => {
        console.log(data);
        this.formFieldsDto = data;
        this.formFields = data.formFields;
        this.authenticationService.setProcessInstanceId(data.processInstanceId);
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
    this.scientificWorkService.saveScientificWorkMagazine(o, this.formFieldsDto.taskId).subscribe(
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
