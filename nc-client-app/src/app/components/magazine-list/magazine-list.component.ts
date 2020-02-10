import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { Router, ActivatedRoute } from '@angular/router';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { PaymentService } from 'src/app/services/payment/payment.service';

@Component({
  selector: 'app-magazine-list',
  templateUrl: './magazine-list.component.html',
  styleUrls: ['./magazine-list.component.css']
})
export class MagazineListComponent implements OnInit {

  magazines = [];
  roles = [];
  username = null;
  subscriptionId = null;

  constructor(private authenticationService: AuthenticationService,
    private util: Util,
    private route: ActivatedRoute,
    private router: Router,
    private magazineService: MagazineService,
    private paymentService: PaymentService) { }

  ngOnInit() {
    this.username = this.authenticationService.getUsername();
    console.log(this.username);
    this.magazineService.getActiveMagazines(this.username).subscribe(
      (data: any) => {
        console.log(data);
        this.magazines = data;

        this.subscriptionId = this.route.snapshot.paramMap.get('id');
        console.log(this.subscriptionId);
        if (this.subscriptionId != null) {
          this.paymentService.completeSubscription(this.subscriptionId).subscribe(
            (data: any) => {
              console.log(data);
              let magazine: any;
              for (magazine in this.magazines) {
                if (magazine.id === data.id) {
                  magazine.flag = data.flag;
                }
              }
              this.router.navigate(['/magazine_list']);
              if (data.flag == true) {
                this.util.showSnackBar('You have successfully subscribe for magazine!');
              } else {
                this.util.showSnackBar('You canceled your subscription!');
              }
            },
            () => {
              this.util.showSnackBar('Error while completing subscription process!');
            }
          );
        }
      },
      () => {
        this.util.showSnackBar("Error occured");
      }
    )
  }

  subscribe(magazine: any) {
    let subscriptionDto = {
      magazineId: magazine.id, merchantId: magazine.merchantId, username: this.username
    }
    console.log(subscriptionDto);
    this.paymentService.prepareSubscription(subscriptionDto).subscribe(
      (data: any) => {
        console.log(data);
        if (data) {
          window.location.href = data.redirectionUrl;
        }
      }
    )
  }

  cancelSubscription(magazine: any) {
    let cancelDto = {
      username: this.username, magazineId: magazine.id
    }
    console.log(cancelDto);
    this.paymentService.cancelSubscription(cancelDto).subscribe(
      (data: any) => {
        console.log(data);
        if (data) {
          if (data.cancellationFlag) {
            this.util.showSnackBar("Cancelation process succesfully completed");
            magazine.flag = true;
          } else {
            this.util.showSnackBar(data.cancellationMessage);
          }
        }
      },
      () => {
        this.util.showSnackBar('Error while canceling subscription!');
      }
    )
  }
}
