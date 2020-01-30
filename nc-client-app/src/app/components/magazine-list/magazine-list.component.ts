import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Util } from 'src/app/utils';
import { Router } from '@angular/router';
import { MagazineService } from 'src/app/services/magazine/magazine.service';

@Component({
  selector: 'app-magazine-list',
  templateUrl: './magazine-list.component.html',
  styleUrls: ['./magazine-list.component.css']
})
export class MagazineListComponent implements OnInit {

  magazines = [];
  roles = [];
  flag = false;

  constructor(private authenticationService: AuthenticationService,
    private util: Util,
    private magazineService: MagazineService) { }

  ngOnInit() {
    let roles = this.authenticationService.getRoles();
    console.log(roles);
    if (roles != [] && roles != null) {
      this.flag = true;
    }
    this.magazineService.getActiveMagazines().subscribe(
      (data: any) => {
        console.log(data);
        this.magazines = data;
      },
      () => {
        this.util.showSnackBar("Error occured");
      }
    )
  }
}
