import { Component, OnInit, Inject } from '@angular/core';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Util } from 'src/app/utils';

@Component({
  selector: 'app-profile-magazine-dialog',
  templateUrl: './profile-magazine-dialog.component.html',
  styleUrls: ['./profile-magazine-dialog.component.css']
})
export class ProfileMagazineDialogComponent implements OnInit {

  private formFields = [];
  private formFieldsDto = null;

  constructor(private magazineService: MagazineService,
    private dialogRef: MatDialogRef<ProfileMagazineDialogComponent>,
    private util: Util,
    @Inject(MAT_DIALOG_DATA) public taskId: any) {

    this.magazineService.getMagazineFormFields(taskId).subscribe(
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
    if (value['validni_podaci'] == "") {
      value['validni_podaci'] = false;
    }
    console.log(value['validni_podaci']);
    let checkReviewerDto = { taskId: this.taskId, flag: value['validni_podaci'] };
    console.log(checkReviewerDto);
    this.magazineService.checkMagazine(checkReviewerDto).subscribe(
      (data) => {
        console.log(data);
        this.dialogRef.close(value['validni_podaci']);
      },
      (err) => {
        this.util.showSnackBar("Error occured");
      }
    )

  }

}
