import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Banka } from 'src/app/models/banka';
import { Filijala } from 'src/app/models/filijala';
import { BankaService } from 'src/app/services/banka.service';
import { FilijalaService } from 'src/app/services/filijala.service';

@Component({
  selector: 'app-filijala-dialog',
  templateUrl: './filijala-dialog.component.html',
  styleUrls: ['./filijala-dialog.component.css']
})
export class FilijalaDialogComponent implements OnInit {

  public flag!: number;
  public banke!: Banka[];


  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<FilijalaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Filijala,
    public filijalaService: FilijalaService,
    public bankaService: BankaService) { }



  ngOnInit(): void {
    this.bankaService.getAllBanks().subscribe(
      (data) => {
        this.banke = data;
      })
  }

  compareTo(a: any, b: any) {
    return a.id == b.id
  }

  public addFilijala(): void {
    this.filijalaService.addFilijala(this.data).subscribe(() => {
      this.snackBar.open("Uspesno dodata filijala sa adresom " + this.data.adresa, 'OK', {
        duration: 2500
      })
    },
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
        this.snackBar.open("Doslo je do greske prilikom dodavanja filijale ", 'Zatvori', {
          duration: 2500
        })
      }
    )
  }

  public updateFilijala(): void {
    this.filijalaService.updateFilijala(this.data.id, this.data).subscribe(() => {
      this.snackBar.open("Uspesno modifikovana filijala sa adresom " + this.data.adresa, 'OK', {
        duration: 2500
      })
    },
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
        this.snackBar.open("Doslo je do greske prilikom modifikacije filijale ", 'Zatvori', {
          duration: 2500
        })
      }
    )
  }
  public deleteFilijala(): void {
    this.filijalaService.deleteFilijala(this.data.id).subscribe(() => {
      this.snackBar.open("Uspesno obrisana filijala sa adresom " + this.data.adresa, 'OK', {
        duration: 2500
      })
    },
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
        this.snackBar.open("Doslo je do greske prilikom modifikacije filijale ", 'Zatvori', {
          duration: 2500
        })
      }
    )
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste', 'Zatvori', {
      duration: 1000
    })
  }
}

