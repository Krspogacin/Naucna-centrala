import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { RegistrationDialogComponent } from './components/registration-dialog/registration-dialog.component';
import { AuthenticationService } from './services/authentication/authentication.service';
import { Router } from '@angular/router';
import { Util } from './utils';
import { LoginComponent } from './components/login/login.component';
import { ChooseMagazineDialogComponent } from './components/choose-magazine-dialog/choose-magazine-dialog.component';
import { ScientificWorkInfoDialogComponent } from './components/scientific-work-info-dialog/scientific-work-info-dialog.component';
import { CoauthorDialogComponent } from './components/coauthor-dialog/coauthor-dialog.component';
import { UploadFileDialogComponent } from './components/upload-file-dialog/upload-file-dialog.component';
import { UploadService } from './services/upload/upload.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  username: string;
  roleEditor: any = null;
  roleAdmin: any = null;
  roleReviewer: any = null;

  constructor(
    private dialog: MatDialog,
    private authenticationService: AuthenticationService,
    private uploadService: UploadService,
    private router: Router,
    private util: Util) { }

  ngOnInit() {
    let roles = this.authenticationService.getRoles();
    for (var role in roles) {
      if (roles[role] == "ROLE_EDITOR") {
        this.roleEditor = true;
      } else if (roles[role] == "ROLE_ADMINISTRATOR") {
        this.roleAdmin = true;
      } else if (roles[role] == "ROLE_REVIEWER") {
        this.roleReviewer = true;
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

  openAddWorkDialog() {
    this.username = this.authenticationService.getUsername();
    console.log(this.username);
    if (this.username == null) {
      if (confirm("Da bi ste dodali rad morate biti ulogovani. Da li želite da pokrenete proces registracije?")) {
        this.openRegistrationDialog();
      }
    } else {
      const dialogRef = this.dialog.open(ChooseMagazineDialogComponent,
        {
          data: this.username,
          disableClose: true,
          autoFocus: true,
          width: '50%'
        });
      dialogRef.afterClosed().subscribe(
        () => {
          const dialogRef2 = this.dialog.open(ScientificWorkInfoDialogComponent,
            {
              data: this.username,
              disableClose: true,
              autoFocus: true,
              width: '50%'
            });
          dialogRef2.afterClosed().subscribe(
            () => {
              this.openCoauthorWindow();
            });
        },
        () => {
          this.util.showSnackBar('Error while closing dialog!');
        });
    }
  }

  openCoauthorWindow() {
    const dialogRef3 = this.dialog.open(CoauthorDialogComponent,
      {
        data: this.username,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef3.afterClosed().subscribe(
      (flag: any) => {
        if (flag == true) {
          this.openCoauthorWindow();
        } else {
          const dialogRef4 = this.dialog.open(UploadFileDialogComponent,
            {
              data: this.username,
              disableClose: true,
              autoFocus: true,
              width: '50%'
            });
          dialogRef4.afterClosed().subscribe(
            () => {
              this.util.showSnackBar('Successfully added scientific work!');
            });
        }
      });
  }

  logout() {
    setTimeout(() => {
      this.authenticationService.removeUsername();
      this.authenticationService.removeAccessToken();
      this.authenticationService.removeRoles();
      this.router.navigate(['/']);
      this.roleAdmin = null;
      this.roleEditor = null;
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
              this.roleEditor = true;
            } else if (roles[role] == "ROLE_ADMINISTRATOR") {
              this.roleAdmin = true;
            } else if (roles[role] == "ROLE_REVIEWER") {
              this.roleReviewer = true;
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
