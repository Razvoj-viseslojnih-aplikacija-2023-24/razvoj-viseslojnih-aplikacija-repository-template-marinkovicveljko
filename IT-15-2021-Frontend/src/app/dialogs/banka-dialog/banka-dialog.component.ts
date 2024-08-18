import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar'
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog'
import { Banka } from 'src/app/models/banka';
import { BankaService } from 'src/app/services/banka.service';

@Component({
  selector: 'app-banka-dialog',
  templateUrl: './banka-dialog.component.html',
  styleUrls: ['./banka-dialog.component.css']
})
export class BankaDialogComponent implements OnInit {

  public flag!: number;

  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<BankaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Banka,
    public bankaService: BankaService) { }


  ngOnInit(): void {
    
  }

  public addBanka(): void {
    this.bankaService.addBanka(this.data).subscribe(() => {
      this.snackBar.open('Uspesno dodata banka ' + this.data.naziv, 'OK', {
        duration: 2500
      })
    }),
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
        this.snackBar.open('Doslo je do greske prilikom dodavanja artikla ', 'Zatvori', {
          duration: 2500
        })
      }
  }

  public updateBanka(): void {
    this.bankaService.updateBanka(this.data.id, this.data).subscribe(() => {
      this.snackBar.open('Uspesno modifikovana banka ' + this.data.naziv, 'OK', {
        duration: 2500
      })
    }),
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
        this.snackBar.open('Doslo je do greske prilikom azuriranja artikla ', 'Zatvori', {
          duration: 2500
        })
      }
  }

  public deleteBanka(): void {
    this.bankaService.deleteBanka(this.data.id).subscribe(() => {
      this.snackBar.open("Uspesno obrisan artikl " + this.data.naziv, 'OK', {
        duration: 2500
      })
    }),
      (error: Error) => {
        console.log(error.name + ' ' + error.message)
        this.snackBar.open('Doslo je do greske prilikom brisanja artikla ', 'Zatvori', {
          duration: 2500
        })
      }
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste.', 'Zatvori', {
      duration: 1000
    })
  }
}
