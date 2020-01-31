import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { ActivatedRoute, Router } from '@angular/router';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { PaymentService } from 'src/app/services/payment/payment.service';

@Component({
  selector: 'app-scientific-work',
  templateUrl: './scientific-work.component.html',
  styleUrls: ['./scientific-work.component.css']
})
export class ScientificWorkComponent implements OnInit {

  loadingWorks = false;
  loadingEdition = false;
  id: number;
  edition: any;
  works = [];
  username = null;
  magazine = null;
  orderId = null;

  constructor(private authenticationService: AuthenticationService,
    private util: Util,
    private route: ActivatedRoute,
    private router: Router,
    private paymentService: PaymentService,
    private magazineService: MagazineService) { }

  ngOnInit() {
    const id = parseInt(this.route.snapshot.paramMap.get('id'), 10);
    this.id = id;
    if (!isNaN(id)) {
      this.magazineService.getEdition(id).subscribe(
        (data: any) => {
          console.log(data);
          this.edition = data;
          this.magazineService.getMagazine(this.edition.magazineId).subscribe(
            (data: any) => {
              console.log(data);
              this.magazine = data;
            }
          );

          this.orderId = this.route.snapshot.paramMap.get('orderId');
          console.log(this.orderId);
          if (this.orderId != null) {
            this.paymentService.completePayment(this.orderId).subscribe(
              (data: any) => {
                console.log(data);
                let work: any;
                for (work in this.works) {
                  if (work.id === data.id) {
                    work.flag = data.flag;
                  }
                }
                this.router.navigate(['/scientific_work/'.concat(this.edition.id)]);
                if (data.flag == true) {
                  this.util.showSnackBar('You have successfully pay for scietific work!');
                } else {
                  this.util.showSnackBar('You canceled you payment!');
                }
              },
              () => {
                this.util.showSnackBar('Error while completing payment process!');
              }
            );
          }
          this.loadingEdition = true;
        }
      );
      this.username = this.authenticationService.getUsername();
      console.log(this.username);
      this.magazineService.getWorks(id, this.username).subscribe(
        (data: any) => {
          console.log(data);
          this.works = data;
          this.loadingWorks = true;
        }
      )
    }
  }
  buyScientificWork(work: any) {
    let paymentDto = {
      merchantId: this.magazine.merchantId, item: work.title,
      price: work.price, description: work.description, returnUrl: this.router.url,
      itemType: "WORK", itemId: work.id, username: this.username
    }
    console.log(paymentDto);
    this.paymentService.preparePayment(paymentDto).subscribe(
      (data: any) => {
        console.log(data);
        if (data) {
          window.location.href = data.redirectionUrl;
        }
      }
    )
  }
}
