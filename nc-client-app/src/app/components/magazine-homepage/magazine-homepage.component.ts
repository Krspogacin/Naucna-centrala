import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { MatDialog } from '@angular/material';
import { Util } from 'src/app/utils';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { MagazineComponent } from '../magazine/magazine.component';
import { MagazineERComponent } from '../magazine-er/magazine-er.component';

@Component({
  selector: 'app-magazine-homepage',
  templateUrl: './magazine-homepage.component.html',
  styleUrls: ['./magazine-homepage.component.css']
})
export class MagazineHomepageComponent implements OnInit {

  role: any = null;
  magazines = [];
  urednik = null;

  constructor(private authenticationService: AuthenticationService,
    private magazineService: MagazineService,
    private dialog: MatDialog,
    private util: Util) { }

  ngOnInit() {
    let roles = this.authenticationService.getRoles();
    for (var role in roles) {
      if (roles[role] == "ROLE_EDITOR") {
        this.role = roles[role];
        let username = this.authenticationService.getUsername();
        this.urednik = username;
        this.magazineService.getMagazines(username).subscribe(
          (data: any) => {
            console.log(data);
            this.magazines = data;
          }
        )
      }
    }
  }

  createMagazine() {
    const dialogRef = this.dialog.open(MagazineComponent,
      {
        data: this.urednik,
        disableClose: true,
        autoFocus: true,
        width: '40%'
      });
    dialogRef.afterClosed().subscribe(
      (issn) => {
        let transferObj = { urednik: this.urednik, issn: issn }
        const dialogRef2 = this.dialog.open(MagazineERComponent,
          {
            data: transferObj,
            disableClose: true,
            autoFocus: true,
            width: '40%'
          });
      },
      () => {
        this.util.showSnackBar('Error while closing dialog!');
      }
    );
  }

  updateMagazine() {
    const dialogRef = this.dialog.open(MagazineComponent,
      {
        data: null,
        disableClose: true,
        autoFocus: true,
        width: '40%'
      });
    dialogRef.afterClosed().subscribe(
      (issn) => {
        let transferObj = { urednik: this.urednik, issn: issn }
        const dialogRef2 = this.dialog.open(MagazineERComponent,
          {
            data: transferObj,
            disableClose: true,
            autoFocus: true,
            width: '40%'
          });
      },
      () => {
        this.util.showSnackBar('Error while closing dialog!');
      }
    );
  }

}

