import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { RegistrationDialogComponent } from './components/registration-dialog/registration-dialog.component';
import { AuthenticationService } from './services/authentication/authentication.service';
import { Router } from '@angular/router';
import { Util } from './utils';
import { LoginComponent } from './components/login/login.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  username: string;
  role: any = null;

  constructor(
    private dialog: MatDialog,
    private authenticationService: AuthenticationService,
    private router: Router,
    private util: Util) { }

  ngOnInit() {
    let roles = this.authenticationService.getRoles();
    for (var role in roles) {
      if (roles[role] == "ROLE_EDITOR") {
        this.role = roles[role];
      }
    }
    this.username = this.authenticationService.getUsername();
    this.authenticationService.authSubject.subscribe(
      (data) => {
        if (data.key == this.authenticationService.usernameKey) {
          this.username = data.value
        }
      }
    )
  }

  openRegistrationDialog() {
    const dialogRef = this.dialog.open(RegistrationDialogComponent,
      {
        data: undefined,
        disableClose: true,
        autoFocus: true,
        width: '40%'
      });
  }

  logout() {
    setTimeout(() => {
      this.authenticationService.removeUsername();
      this.authenticationService.removeAccessToken();
      this.authenticationService.removeRoles();
      this.router.navigate(['/']);
      this.util.showSnackBar('Logged out successfully!');
    }, 500);
  }

  openLoginDialog() {
    const dialogRef = this.dialog.open(LoginComponent, {
      data: undefined,
      disableClose: true,
      autoFocus: true,
      width: '40%'
    });
    dialogRef.afterClosed().subscribe(
      (data: any) => {
        if (data) {
          let roles = this.authenticationService.getRoles();
          for (var role in roles) {
            if (roles[role] == "ROLE_EDITOR") {
              this.role = roles[role];
            }
          }
          this.router.navigateByUrl('/');
          this.util.showSnackBar('You have logged in successfully!');
        }
      },
      (error) => {
        this.util.showSnackBar('Error in logging process!');
      }
    )
  }
}
