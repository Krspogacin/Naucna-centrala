import { Component, OnInit, Inject } from '@angular/core';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-magazine',
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.css']
})
export class MagazineComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  private enumValues = [];
  private enumValues2 = [];

  constructor(private magazineService: MagazineService,
    private authenticationService: AuthenticationService,
    private util: Util,
    private dialogRef: MatDialogRef<MagazineComponent>,
    @Inject(MAT_DIALOG_DATA) public urednik: any) {

    if (urednik != null) {
      this.magazineService.startProcess(urednik).subscribe(
        (data: any) => {
          console.log(data);
          this.formFieldsDto = data;
          this.formFields = data.formFields;
          this.authenticationService.setProcessInstanceId(data.processInstanceId);
          this.formFields.forEach((field) => {

            if (field.type.name == 'enum') {
              this.enumValues = Object.keys(field.type.values);
              console.log(this.enumValues);
            }
            if (field.type.name == 'multipleEnum_naucne_oblasti') {
              this.enumValues2 = Object.keys(field.type.values);
              console.log(this.enumValues2);
            }
          });
        },
        (err) => {
          this.util.showSnackBar("Error occured");
        }
      )
    } else {
      let processInstanceId = this.authenticationService.getProcessInstanceId();
      this.magazineService.getUpdatedFormFields(processInstanceId).subscribe(
        (data: any) => {
          console.log(data);
          this.formFieldsDto = data;
          this.formFields = data.formFields;
          this.authenticationService.setProcessInstanceId(data.processInstanceId);
          this.formFields.forEach((field) => {

            if (field.type.name == 'enum') {
              this.enumValues = Object.keys(field.type.values);
              console.log(this.enumValues);
            }
            if (field.type.name == 'multipleEnum_naucne_oblasti') {
              this.enumValues2 = Object.keys(field.type.values);
              console.log(this.enumValues2);
            }
          });
        },
        (err) => {
          this.util.showSnackBar("Error occured");
        }
      )
    }

  }

  ngOnInit() {
  }

  onSubmit(value) {
    let o = new Array();
    for (var property in value) {
      o.push({ fieldId: property, fieldValue: value[property] });
    }

    console.log(o);
    this.magazineService.saveMagazine(o, this.formFieldsDto.taskId).subscribe(
      (data) => {
        this.dialogRef.close(value['issn']);
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      }
    );
  }

}
