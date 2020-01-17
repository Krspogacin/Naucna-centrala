import { Component, OnInit } from '@angular/core';
import { Util } from 'src/app/utils';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-verify',
  templateUrl: './verify.component.html',
  styleUrls: ['./verify.component.css']
})
export class VerifyComponent implements OnInit {

  username: any;

  constructor(private authenticationService: AuthenticationService,
    private util: Util,
    private router: ActivatedRoute,
    private userService: UserService,
    private router2: Router) { }

  ngOnInit() {
    const processInstaceId = this.authenticationService.getProcessInstanceId();
    if (!processInstaceId) {
      this.util.showSnackBar("Doslo je do greske prilikom verifikaciji korisnika");
      return;
    }
    console.log(processInstaceId);
    this.router.queryParams.subscribe(
      data => {
        this.username = data['user'];
      }
    );
    if (!this.username) {
      this.util.showSnackBar("Doslo je do greske prilikom verifikaciji korisnika");
      return;
    }
    console.log(this.username);
    this.userService.verifyUser(this.username, processInstaceId).subscribe(
      () => {
        this.router2.navigate(['/']);
        this.util.showSnackBar('Uspesno ste verifikovali svoj nalog!');
      },
      (error) => {
        this.util.showSnackBar('Doslo je do greske prilikom verifikaciji korisnika!');
      }
    );
  }

}
