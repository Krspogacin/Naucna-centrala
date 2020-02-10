import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { MatDialog } from '@angular/material';
import { Util } from 'src/app/utils';
import { MagazineService } from 'src/app/services/magazine/magazine.service';
import { MagazineComponent } from '../magazine/magazine.component';
import { MagazineERComponent } from '../magazine-er/magazine-er.component';
import { PaymentService } from 'src/app/services/payment/payment.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ScientificWorkService } from 'src/app/services/scientific-work/scientific-work.service';
import { CheckScientificWorkDialogComponent } from '../check-scientific-work-dialog/check-scientific-work-dialog.component';
import { UploadService } from 'src/app/services/upload/upload.service';
import { CheckPdfDialogComponent } from '../check-pdf-dialog/check-pdf-dialog.component';
import { SetCorrectionTimeDialogComponent } from '../set-correction-time-dialog/set-correction-time-dialog.component';
import { SelectReviewersDialogComponent } from '../select-reviewers-dialog/select-reviewers-dialog.component';
import { SetReviewTimeDialogComponent } from '../set-review-time-dialog/set-review-time-dialog.component';
import { ReviewResultsDialogComponent } from '../review-results-dialog/review-results-dialog.component';
import { EditorDecisionDialogComponent } from '../editor-decision-dialog/editor-decision-dialog.component';
import { SetChangingTimeDialogComponent } from '../set-changing-time-dialog/set-changing-time-dialog.component';
import { EditorFinalDecisionDialogComponent } from '../editor-final-decision-dialog/editor-final-decision-dialog.component';
import { SetChangingTimeAgainDialogComponent } from '../set-changing-time-again-dialog/set-changing-time-again-dialog.component';
import { NewReviewersDialogComponent } from '../new-reviewers-dialog/new-reviewers-dialog.component';

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
  tasks = [];
  tasks2 = [];
  tasks3 = [];
  tasks4 = [];
  tasks5 = [];
  tasks6 = [];
  tasks7 = [];
  tasks8 = [];
  username = null;

  constructor(private authenticationService: AuthenticationService,
    private magazineService: MagazineService,
    private dialog: MatDialog,
    private scientificWorkService: ScientificWorkService,
    private uploadService: UploadService,
    private util: Util,
    private route: ActivatedRoute,
    private router: Router,
    private paymentService: PaymentService) { }

  ngOnInit() {
    let roles = this.authenticationService.getRoles();
    for (var role in roles) {
      if (roles[role] == "ROLE_EDITOR") {
        this.role = roles[role];
        this.username = this.authenticationService.getUsername();
        this.urednik = this.username;
        this.magazineService.getMagazines(this.username).subscribe(
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

        this.scientificWorkService.getCheckScientificWorksTasks(this.username).subscribe(
          (data: any) => {
            console.log(data);
            this.tasks = data;
          },
          (error) => {
            this.util.showSnackBar("Error while getting tasks");
          });

        this.scientificWorkService.getCheckPdfTasks(this.username).subscribe(
          (data: any) => {
            console.log(data);
            this.tasks2 = data;
          },
          (error) => {
            this.util.showSnackBar("Error while getting tasks");
          });

        this.scientificWorkService.getSelectReviewersTasks(this.username).subscribe(
          (data: any) => {
            console.log(data);
            this.tasks3 = data;
          },
          (error) => {
            this.util.showSnackBar("Error while getting tasks");
          });

        this.scientificWorkService.getReviewerSetTimeTasks(this.username).subscribe(
          (data: any) => {
            console.log(data);
            this.tasks4 = data;
          },
          (error) => {
            this.util.showSnackBar("Error while getting tasks");
          });

        this.scientificWorkService.getReviewResultsTasks(this.username).subscribe(
          (data: any) => {
            console.log(data);
            this.tasks5 = data;
          },
          (error) => {
            this.util.showSnackBar("Error while getting tasks");
          });

        this.scientificWorkService.getEditorDecisionTasks(this.username).subscribe(
          (data: any) => {
            console.log(data);
            this.tasks6 = data;
          },
          (error) => {
            this.util.showSnackBar("Error while getting tasks");
          });

        this.scientificWorkService.getViewingWorkTasks(this.username).subscribe(
          (data: any) => {
            console.log(data);
            this.tasks7 = data;
          },
          (error) => {
            this.util.showSnackBar("Error while getting tasks");
          });

        this.scientificWorkService.getNewReviewersTasks(this.username).subscribe(
          (data: any) => {
            console.log(data);
            this.tasks8 = data;
          },
          (error) => {
            this.util.showSnackBar("Error while getting tasks");
          });
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

  checkScientificWork(task) {
    const dialogRef = this.dialog.open(CheckScientificWorkDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      (data: any) => {
        task.flag = data;
        console.log(data);
        this.util.showSnackBar('Succesfully checked scientific work!');
        if (data === true) {
          this.scientificWorkService.getCheckPdfTasks(this.username).subscribe(
            (data: any) => {
              console.log(data);
              this.tasks2 = data;
            },
            (error) => {
              this.util.showSnackBar("Error while getting tasks");
            });
        }
      },
      (error) => {
        this.util.showSnackBar('Error while checking reviewer!');
      });
  }

  getScientificWork(task) {
    this.uploadService.download(task.id).subscribe(
      (data: any) => {
        const url = window.URL.createObjectURL(data);
        window.open(url);
      },
      (error) => {
        console.log('Greska: ' + error);;
      });
  }

  checkPDF(task) {
    const dialogRef = this.dialog.open(CheckPdfDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      (data: any) => {
        task.flag = data.flag;
        console.log(data);
        if (data.flag == true) {
          this.util.showSnackBar('Succesfully checked pdf format!');
        } else {
          const dialogRef = this.dialog.open(SetCorrectionTimeDialogComponent,
            {
              data: data.processInstanceId,
              disableClose: true,
              autoFocus: true,
              width: '50%'
            });
          dialogRef.afterClosed().subscribe(
            () => {
              this.util.showSnackBar('Succesfully checked pdf format!');
            }
          )
        }
      },
      (error) => {
        this.util.showSnackBar('Error while checking reviewer!');
      });
  }

  selectReviewers(task) {
    const dialogRef = this.dialog.open(SelectReviewersDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      () => {
        this.scientificWorkService.getReviewerSetTimeTasks(this.username).subscribe(
          (data: any) => {
            console.log(data);
            this.tasks4 = data;
          },
          (error) => {
            this.util.showSnackBar("Error while getting tasks");
          });
        task.flag = true;
        this.util.showSnackBar('Reviewers successfully selected!');
      }
    )
  }

  setTime(task) {
    const dialogRef = this.dialog.open(SetReviewTimeDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      () => {
        task.flag = true;
        this.util.showSnackBar('Review time successfully set!');
      });
  }

  reviewResult(task) {
    const dialogRef = this.dialog.open(ReviewResultsDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      () => {
        this.scientificWorkService.getEditorDecisionTasks(this.username).subscribe(
          (data: any) => {
            console.log(data);
            this.tasks6 = data;
          },
          (error) => {
            this.util.showSnackBar("Error while getting tasks");
          });
        task.flag = true;
      });
  }

  getDecision(task) {
    const dialogRef = this.dialog.open(EditorDecisionDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      (data: any) => {
        if (data.odluka === "prihvatiti_uz_manje_ispravke" || data.odluka === "uslovno_prihvatiti_uz_vece_ispravke") {
          const dialogRef = this.dialog.open(SetChangingTimeDialogComponent,
            {
              data: data.processInstanceId,
              disableClose: true,
              autoFocus: true,
              width: '50%'
            });
          dialogRef.afterClosed().subscribe(
            () => {
              task.flag = true;
              this.util.showSnackBar('Final decision successfully set!');
            }
          )
        } else {
          task.flag = true;
          this.util.showSnackBar('Final decision successfully set!');
        }
      });
  }

  getFinalDecision(task) {
    const dialogRef = this.dialog.open(EditorFinalDecisionDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      (data: any) => {
        if (data.odluka === "dorada") {
          const dialogRef = this.dialog.open(SetChangingTimeAgainDialogComponent,
            {
              data: data.processInstanceId,
              disableClose: true,
              autoFocus: true,
              width: '50%'
            });
          dialogRef.afterClosed().subscribe(
            () => {
              task.flag = true;
              this.util.showSnackBar('Final decision successfully set!');
            }
          )
        } else {
          task.flag = true;
          this.util.showSnackBar('Final decision successfully set!');
        }
      }
    );
  }

  getNewReviewers(task) {
    const dialogRef = this.dialog.open(NewReviewersDialogComponent,
      {
        data: task.id,
        disableClose: true,
        autoFocus: true,
        width: '50%'
      });
    dialogRef.afterClosed().subscribe(
      () => {
        task.flag = true;
        this.util.showSnackBar('Reviewers successfully selected!');
      });
  }

}

