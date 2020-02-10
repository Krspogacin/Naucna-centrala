import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { UploadService } from 'src/app/services/upload/upload.service';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-upload-file-dialog',
  templateUrl: './upload-file-dialog.component.html',
  styleUrls: ['./upload-file-dialog.component.css']
})
export class UploadFileDialogComponent implements OnInit {

  form: FormGroup;
  error: string;
  uploadResponse = { status: '', message: '' };
  private formFields = [];
  private formFieldsDto = null;
  private enumValues = [];

  constructor(private formBuilder: FormBuilder,
    private uploadService: UploadService,
    private scientificWorkService: ScientificWorkService,
    private authenticationService: AuthenticationService,
    private util: Util,
    private dialogRef: MatDialogRef<UploadFileDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public username: any) {

    console.log(username);
    const processInstaceId = this.authenticationService.getProcessInstanceId();
    if (!processInstaceId) {
      this.util.showSnackBar("Doslo je do greske prilikom kreiranja naucnog rada");
      return;
    }
    console.log(processInstaceId);

    this.scientificWorkService.getPdfFormFields(processInstaceId, username).subscribe(
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
