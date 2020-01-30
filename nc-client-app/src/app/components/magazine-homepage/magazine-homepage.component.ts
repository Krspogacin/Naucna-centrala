import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { MatDialog } from '@angular/material';
import { Util } from 'src/app/utils';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { MagazineComponent } from '../magazine/magazine.component';
import { MagazineERComponent } from '../magazine-er/magazine-er.component';
import { PaymentService } from 'src/app/services/payment/payment.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-magazine-homepage',
  templateUrl: './magazine-homepage.component.html',
  styleUrls: ['./magazine-homepage.component.css']
})
export class MagazineHomepageComponent implements OnInit {

  role: any = null;
  magazines = [];
  urednik = null;
  merchantId: string = null;

  constructor(private authenticationService: AuthenticationService,
    private magazineService: MagazineService,
    private dialog: MatDialog,
    private util: Util,
    private route: ActivatedRoute,
    private router: Router,
    private paymentService: PaymentService) { }

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
        this.merchantId = this.route.snapshot.paramMap.get('id');
        console.log(this.merchantId);
        if (this.merchantId != null) {
          this.paymentService.completeRegistration(this.merchantId).subscribe(
            (data: any) => {
              console.log(data);
              let magazine: any;
              for (magazine in this.magazines) {
                if (magazine.merchantId === this.merchantId) {
                  magazine.isMerchant = data.flag;
                }
              }
              this.router.navigate(['/magazine']);
              this.util.showSnackBar('You have successfully registrated you magazine as a KP merchant!');
            },
            () => {
              this.util.showSnackBar('Error while completing registration process!');
            }
          );
        }
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

  registerMerchant(magazine: any) {
    console.log(magazine);
    let merchantRequest = { name: magazine.name, issn: magazine.issn };
    this.paymentService.registerMerchant(merchantRequest).subscribe(
      (data: any) => {
        console.log(data);
        if (data) {
          window.location.href = data.redirectionUrl;
        }
      }
    )
  }

}

