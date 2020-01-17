import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<LoginComponent>,
    private authenticationService: AuthenticationService,
    private util: Util) { }

  ngOnInit() {
  }

  onSubmit(value, form) {
    console.log(value);
    this.authenticationService.login(value).subscribe(
      (userState: any) => {
        this.authenticationService.setUsername(userState.username);
        this.authenticationService.setAccessToken(userState.token);
        this.authenticationService.setRoles(userState.roles);
        this.dialogRef.close(true);
      },
      (error) => {
        this.util.showSnackBar("Error message!")
      }
    )
  }

}
