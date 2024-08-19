import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { KorisniciDialogComponent } from 'src/app/dialogs/korisnici-dialog/korisnici-dialog.component';
import { Korisnici } from 'src/app/models/korisnici';
import { KorisniciService } from 'src/app/services/korisnici.service';
import { UslugaComponent } from '../usluga/usluga.component';

@Component({
  selector: 'app-korisnici',
  templateUrl: './korisnici.component.html',
  styleUrls: ['./korisnici.component.css']
})
export class KorisniciComponent implements OnInit, OnDestroy {
  displayedColumns = ['id', 'ime', 'prezime', 'maticniBroj', 'actions']
  dataSource!: MatTableDataSource<Korisnici>;
  subscription!: Subscription;

  constructor(private korisniciService: KorisniciService,
    private dialog: MatDialog) { }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData();
  }

  public loadData() {
    this.subscription = this.korisniciService.getAllKorisnici().subscribe(
      data => {
        this.dataSource = new MatTableDataSource(data);

      }
    ),
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
      }
  }

  public openDialog(flag: number, id?: number, ime?: string, prezime?: string, maticniBroj?: number): void {
    const dialogRef = this.dialog.open(KorisniciDialogComponent, { data: { id, ime, prezime, maticniBroj } })

    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(res => {
      if (res == 1) {
        this.loadData();
      }
    })
  }
}
