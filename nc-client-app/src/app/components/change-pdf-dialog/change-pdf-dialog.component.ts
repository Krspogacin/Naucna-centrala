import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { UploadService } from 'src/app/services/upload/upload.service';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-change-pdf-dialog',
  templateUrl: './change-pdf-dialog.component.html',
  styleUrls: ['./change-pdf-dialog.component.css']
})
export class ChangePdfDialogComponent implements OnInit {

  form: FormGroup;
  error: string;
  uploadResponse = { status: '', message: '' };
  private formFields = [];
  private formFieldsDto = null;
  private username = null;


  constructor(private formBuilder: FormBuilder,
    private uploadService: UploadService,
    private scientificWorkService: ScientificWorkService,
    private authenticationService: AuthenticationService,
    private util: Util,
    private dialogRef: MatDialogRef<ChangePdfDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public taskId: any) {

    console.log(taskId);
    this.username = authenticationService.getUsername();
    console.log(this.username);

    this.scientificWorkService.changePdfFormFields(taskId, this.username).subscribe(
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
    this.form = this.formBuilder.group({
      pdf: ['']
    });
  }

  onFileChange(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.form.get('pdf').setValue(file);
      console.log(file);
    }
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('file', this.form.get('pdf').value);

    this.uploadService.upload(formData, this.formFieldsDto.taskId).subscribe(
      (res: any) => {
        this.uploadResponse = res
        if (this.uploadResponse.status === 'finish') {
          this.dialogRef.close();
        }
      },
      (err) => this.error = err
    );
  }

}
