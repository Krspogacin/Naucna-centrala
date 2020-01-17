import { Component, OnInit, Inject } from '@angular/core';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-magazine-er',
  templateUrl: './magazine-er.component.html',
  styleUrls: ['./magazine-er.component.css']
})
export class MagazineERComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;
  private enumValuesEditori = [];
  private enumValuesRecenzenti = [];

  constructor(private magazineService: MagazineService,
    private authenticationService: AuthenticationService,
    private util: Util,
    private dialogRef: MatDialogRef<MagazineERComponent>,
    @Inject(MAT_DIALOG_DATA) public transferObj: any) {

    const processInstaceId = this.authenticationService.getProcessInstanceId();
    if (!processInstaceId) {
      this.util.showSnackBar("Doslo je do greske prilikom verifikaciji korisnika");
      return;
    }
    console.log(processInstaceId);
    console.log(transferObj);
    this.magazineService.getERFormFields(processInstaceId, transferObj.urednik).subscribe(
      (data: any) => {
        console.log(data);
        this.formFieldsDto = data;
        this.formFields = data.formFields;
        this.formFields.forEach((field) => {

          if (field.type.name == 'multipleEnum_editori') {
            this.enumValuesEditori = Object.keys(field.type.values);
          }
          if (field.type.name == 'multipleEnum_recenzenti') {
            this.enumValuesRecenzenti = Object.keys(field.type.values);
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
    let editors = null;
    let reviewers = null;
    editors = { fieldId: 'editori', fieldValue: value['editori'] }
    reviewers = { fieldId: 'recenzenti', fieldValue: value['recenzenti'] }
    if (editors != null && reviewers != null) {
      let dto = { issn: this.transferObj.issn, editors: editors, reviewers: reviewers }
      this.magazineService.saveER(dto, this.formFieldsDto.taskId).subscribe(
        (data) => {
          this.dialogRef.close();
          this.util.showSnackBar("Successfully added magazine");

        },
        (err) => {
          this.util.showSnackBar("Error occured");
        }
      );
    } else {
      this.util.showSnackBar("Error occured");
    }
  }

}
