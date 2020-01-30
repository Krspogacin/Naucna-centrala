import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { ActivatedRoute, Router } from '@angular/router';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { PaymentService } from 'src/app/services/payment/payment.service';

@Component({
  selector: 'app-edition',
  templateUrl: './edition.component.html',
  styleUrls: ['./edition.component.css']
})
export class EditionComponent implements OnInit {

  loadingEditions = false;
  loadingMagazine = false;
  id: number;
  magazine: any;
  editions = [];
  username = null;
  orderId = null;

  constructor(private authenticationService: AuthenticationService,
    private util: Util,
    private route: ActivatedRoute,
    private magazineService: MagazineService,
    private paymentService: PaymentService,
    private router: Router) { }

  ngOnInit() {
    const id = parseInt(this.route.snapshot.paramMap.get('id'), 10);
    this.id = id;
    if (!isNaN(id)) {
      this.magazineService.getMagazine(id).subscribe(
        (data: any) => {
          console.log(data);
          this.magazine = data;

          this.orderId = this.route.snapshot.paramMap.get('orderId');
          console.log(this.orderId);
          if (this.orderId != null) {
            this.paymentService.completePayment(this.orderId).subscribe(
              (data: any) => {
                console.log(data);
                let edition: any;
                for (edition in this.editions) {
                  if (edition.id === data.id) {
                    edition.flag = data.flag;
                  }
                }
                this.router.navigate(['/edition/'.concat(this.magazine.id)]);
                if (data.flag == true) {
                  this.util.showSnackBar('You have successfully pay for magazine edition!');
                } else {
                  this.util.showSnackBar('You canceled you payment!');
                }
              },
              () => {
                this.util.showSnackBar('Error while completing registration process!');
              }
            );
          }
          this.loadingMagazine = true;
        }
      );
      this.username = this.authenticationService.getUsername();
      console.log(this.username);
      this.magazineService.getEditions(id, this.username).subscribe(
        (data: any) => {
          console.log(data);
          this.editions = data;
          this.loadingEditions = true;
        }
      )
    }
  }

  createEdition() {
    let edition = { description: "Ovo je najbolje izdanje" };
    this.magazineService.saveEdition(this.id, edition).subscribe(
      (data: any) => {
        console.log(data);
        this.editions.push(data);
      }
    )
  }

  buyEdition(edition: any) {
    let paymentDto = {
      merchantId: this.magazine.merchantId, item: "Izdanje od datuma " + edition.creationDate,
      price: edition.price, description: edition.description, returnUrl: this.router.url,
      itemType: "EDITION", itemId: edition.id, username: this.username
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
