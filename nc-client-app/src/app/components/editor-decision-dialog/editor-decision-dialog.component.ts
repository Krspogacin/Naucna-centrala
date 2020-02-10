import { Component, OnInit, Inject } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';
import { Util } from 'src/app/utils';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-editor-decision-dialog',
  templateUrl: './editor-decision-dialog.component.html',
  styleUrls: ['./editor-decision-dialog.component.css']
})
export class EditorDecisionDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  private enumValues = [];
  private username = null;

  constructor(private authenticationService: AuthenticationService,
    private scientificWorkService: ScientificWorkService,
    private util: Util,
    private dialogRef: MatDialogRef<EditorDecisionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public taskId: any) {

    console.log(taskId);
    this.username = authenticationService.getUsername();
    console.log(this.username);

    this.scientificWorkService.editorDecisionFormFields(taskId, this.username).subscribe(
      (data: any) => {
        console.log(data);
        this.formFieldsDto = data;
        this.formFields = data.formFields;
        this.formFields.forEach((field) => {
          if (field.type.name == 'enum') {
            this.enumValues = Object.keys(field.type.values);
            console.log(this.enumValues);
          }
        });
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

    this.scientificWorkService.editorDecision(o, this.taskId).subscribe(
      (data) => {
        let object = { odluka: value['odluka'], processInstanceId: this.formFieldsDto.processInstanceId }
        console.log(object);
        this.dialogRef.close(object);
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      });
  }

}
