import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Korisnici } from 'src/app/models/korisnici';
import { KorisniciService } from 'src/app/services/korisnici.service';

@Component({
  selector: 'app-korisnici-dialog',
  templateUrl: './korisnici-dialog.component.html',
  styleUrls: ['./korisnici-dialog.component.css']
})
export class KorisniciDialogComponent implements OnInit {

  public flag!: number;

  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<KorisniciDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Korisnici,
    public korisniciService: KorisniciService) { }

  ngOnInit(): void {
    
  }

  public addKorisnik(): void {
    this.korisniciService.addKorisnik(this.data).subscribe(()=> {
      this.snackBar.open("Uspesno dodat korisnik: " +this.data.ime + " " + this.data.prezime, 'OK', {
        duration: 2500
      }),
      (error: Error)=>
      {
        console.log(error.name + ' ' + error.message);
        this.snackBar.open("Doslo je do greske", 'Zatvori', {
          duration: 2500
        })
      }
    })
  }

  public updateKorisnik(): void {
    this.korisniciService.updateKorisnik(this.data.id, this.data).subscribe(()=> {
      this.snackBar.open("Uspesno izmenjen korisnik: " +this.data.ime + " " + this.data.prezime, 'OK', {
        duration: 2500
      }),
      (error: Error)=>
      {
        console.log(error.name + ' ' + error.message);
        this.snackBar.open("Doslo je do greske", 'Zatvori', {
          duration: 2500
        })
      }
    })
  }

  public deleteKorisnik(): void {
    this.korisniciService.deleteKorisnik(this.data.id).subscribe(()=> {
      this.snackBar.open("Uspesno obrisan korisnik: " +this.data.ime + " " + this.data.prezime, 'OK', {
        duration: 2500
      }),
      (error: Error)=>
      {
        console.log(error.name + ' ' + error.message);
        this.snackBar.open("Doslo je do greske", 'Zatvori', {
          duration: 2500
        })
      }
    })
  }


  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste.', 'Zatvori', {
      duration: 1000
    })
  }

}
