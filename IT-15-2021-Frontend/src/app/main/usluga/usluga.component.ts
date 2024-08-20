import { Component, Input, OnChanges, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { UslugaDialogComponent } from 'src/app/dialogs/usluga-dialog/usluga-dialog.component';
import { Filijala } from 'src/app/models/filijala';
import { Korisnici } from 'src/app/models/korisnici';
import { Usluga } from 'src/app/models/usluga';
import { UslugaService } from 'src/app/services/usluga.service';

@Component({
  selector: 'app-usluga',
  templateUrl: './usluga.component.html',
  styleUrls: ['./usluga.component.css']
})
export class UslugaComponent implements OnInit, OnChanges, OnDestroy {

displayedColumns = ['id', 'naziv', 'opisUsluge', 'datumUgovora', 'provizija', 'filijala', 'actions'];
dataSource! : MatTableDataSource<Usluga>
subscription!: Subscription;
@ViewChild(MatSort, {static: false}) sort!: MatSort
@ViewChild(MatPaginator, {static: false}) paginator!: MatPaginator


@Input() selektovanKorisnikChild!: Korisnici

constructor(private uslugaService: UslugaService,
            private dialog: MatDialog) {}

ngOnDestroy(): void {
  this.subscription.unsubscribe();
}

ngOnChanges() {
  if(this.selektovanKorisnikChild.id) {
    this.loadData();
  }
}

ngOnInit() : void {
//this.loadData();
}

loadData() {
  this.subscription = this.uslugaService.getAllUslugeZaKorisnika(this.selektovanKorisnikChild.id).subscribe(
    data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }
  ),
  (error: Error) => {
    console.log(error.name + " " + error.message);
  }
}

openDialog(flag: number, id?: number, naziv?: string, opisUsluge?: string, datumUgovora?: Date, provizija?:number,filijala?: Filijala) {
const dialogRef = this.dialog.open(UslugaDialogComponent, {data: {id,naziv,opisUsluge,datumUgovora,provizija,filijala}});
dialogRef.componentInstance.flag = flag;
if(flag == 1) {
  dialogRef.componentInstance.data.korisnik = this.selektovanKorisnikChild;
}
dialogRef.afterClosed().subscribe(res => {
  if(res==1) {
    this.loadData();
  }
})
}
public applyFilter(filterValue: any) {
  filterValue = filterValue.target.value;
  filterValue = filterValue.trim();
  filterValue = filterValue.toLowerCase();
  this.dataSource.filter = filterValue;
}

}
