import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { RepositoryService } from 'src/app/services/repository/repository.service';
import { MatDialogRef } from '@angular/material';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';

@Component({
  selector: 'app-registration-dialog',
  templateUrl: './registration-dialog.component.html',
  styleUrls: ['./registration-dialog.component.css']
})
export class RegistrationDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  private enumValues = [];


  constructor(private userService: UserService,
    private repositoryService: RepositoryService,
    private authenticationService: AuthenticationService,
    private util: Util,
    private dialogRef: MatDialogRef<RegistrationDialogComponent>) {

    this.repositoryService.startProcess().subscribe(
      (data: any) => {
        console.log(data);
        this.formFieldsDto = data;
        this.formFields = data.formFields;
        this.authenticationService.setProcessInstanceId(data.processInstanceId);
        this.formFields.forEach((field) => {

          if (field.type.name == 'enum' || field.type.name == 'multipleEnum_naucne_oblasti') {
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

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      o.push({ fieldId: property, fieldValue: value[property] });
    }

    console.log(o);
    this.userService.registerUser(o, this.formFieldsDto.taskId).subscribe(
      (data) => {
        console.log(data);
        this.dialogRef.close();
        this.util.showSnackBar("You have successfully registered! Open the email and confirm that it is you!")
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      }
    );
  }

}
