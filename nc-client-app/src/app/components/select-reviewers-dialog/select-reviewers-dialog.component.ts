import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Util } from 'src/app/utils';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';

@Component({
  selector: 'app-select-reviewers-dialog',
  templateUrl: './select-reviewers-dialog.component.html',
  styleUrls: ['./select-reviewers-dialog.component.css']
})
export class SelectReviewersDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  private enumValues = [];
  private username = null;
  private message = null;

  constructor(private dialogRef: MatDialogRef<SelectReviewersDialogComponent>,
    private util: Util,
    private authenticationService: AuthenticationService,
    private scientificWorkService: ScientificWorkService,
    @Inject(MAT_DIALOG_DATA) public taskId: any) {

    console.log(taskId);
    this.username = authenticationService.getUsername();
    console.log(this.username);

    this.scientificWorkService.selectReviewersFormFields(taskId, this.username).subscribe(
      (data: any) => {
        console.log(data);
        this.formFieldsDto = data;
        this.formFields = data.formFields;
        this.formFields.forEach((field) => {

          if (field.type.name == 'enum' || field.type.name == 'multipleEnum_recenzenti_iz_casopisa') {
            this.enumValues = Object.keys(field.type.values);
            console.log(this.enumValues)
          }
        });
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      }
    );

  }

  ngOnInit() {
  }

  onSubmit(value) {
    if (this.enumValues.length >= 2 && value['odabrani_recenzent'].length < 2) {
      this.message = "Morate izabrati makar 2 recenzenta";
      return;
    } else {
      this.message = null;
    }

    let o = new Array();
    for (var property in value) {
      o.push({ fieldId: property, fieldValue: value[property] });
    }

    console.log(o);

    this.scientificWorkService.selectReviewers(o, this.taskId).subscribe(
      (data) => {
        this.dialogRef.close();
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      });
  }

}
