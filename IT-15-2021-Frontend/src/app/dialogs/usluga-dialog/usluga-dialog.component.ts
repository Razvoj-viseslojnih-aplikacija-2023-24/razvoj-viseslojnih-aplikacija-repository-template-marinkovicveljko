import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subscription } from 'rxjs';
import { UslugaComponent } from 'src/app/main/usluga/usluga.component';
import { Filijala } from 'src/app/models/filijala';
import { Usluga } from 'src/app/models/usluga';
import { FilijalaService } from 'src/app/services/filijala.service';
import { UslugaService } from 'src/app/services/usluga.service';

@Component({
  selector: 'app-usluga-dialog',
  templateUrl: './usluga-dialog.component.html',
  styleUrls: ['./usluga-dialog.component.css']
})
export class UslugaDialogComponent implements OnInit, OnDestroy {

  filijale!: Filijala[]
  public flag!: number;
  filijalaSubscription!: Subscription;

  constructor(public snackBar: MatSnackBar,
              public dialogRef: MatDialogRef<UslugaComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Usluga,
              public filijalaService: FilijalaService,
              public uslugaService: UslugaService) {}


  ngOnDestroy(): void {
    this.filijalaSubscription.unsubscribe();
  }

  compareTo(a:any,b:any) {
  return a.id == b.id;
  }

  ngOnInit(): void {
this.filijalaSubscription = this.filijalaService.getAllFilijalas().subscribe(
data => {
this.filijale = data;
}
),
(error: Error) => {
  console.log(error.name + ' ' + error.message);
}
}

public addUsluga(): void {
  this.uslugaService.addUsluga(this.data).subscribe(()=> {
    this.snackBar.open("Uspesno dodata usluga: " +this.data.naziv, 'OK', {
      duration: 2500
    })
  },
    (error: Error)=>
    {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open("Doslo je do greske", 'Zatvori', {
        duration: 2500
      })
    }
  )
}

public updateUsluga(): void {
  this.uslugaService.updateUsluga(this.data.id, this.data).subscribe(()=> {
    this.snackBar.open("Uspesno izmenjena usluga: " +this.data.naziv, 'OK', {
      duration: 2500
    })
  },
    (error: Error)=>
    {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open("Doslo je do greske", 'Zatvori', {
        duration: 2500
      })
    }
  )
}

public deleteUsluga(): void {
  this.uslugaService.deleteUsluga(this.data.id).subscribe(()=> {
    this.snackBar.open("Uspesno obrisana usluga: " +this.data.naziv, 'OK', {
      duration: 2500
    })
  },
    (error: Error)=>
    {
      console.log(error.name + ' ' + error.message);
      this.snackBar.open("Doslo je do greske", 'Zatvori', {
        duration: 2500
      })
    }
  )
}


public cancel(): void {
  this.dialogRef.close();
  this.snackBar.open('Odustali ste.', 'Zatvori', {
    duration: 1000
  })
}

}

