import { NgModule } from '@angular/core';
import { MatSnackBar } from '@angular/material';

@NgModule()
export class Util {

    constructor(private snackBar: MatSnackBar) { }

    showSnackBar(message: string) {
        if (!message) {
            return;
        }
        const snackBarRef = this.snackBar.open(message, 'Close', { duration: 5000, verticalPosition: 'top', panelClass: ['snackbar'] });
        snackBarRef.onAction().subscribe(
            () => {
                snackBarRef.dismiss();
            }
        );
    }
}