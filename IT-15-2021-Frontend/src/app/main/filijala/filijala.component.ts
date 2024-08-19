import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { FilijalaDialogComponent } from 'src/app/dialogs/filijala-dialog/filijala-dialog.component';
import { Banka } from 'src/app/models/banka';
import { Filijala } from 'src/app/models/filijala';
import { FilijalaService } from 'src/app/services/filijala.service';

@Component({
  selector: 'app-filijala',
  templateUrl: './filijala.component.html',
  styleUrls: ['./filijala.component.css']
})
export class FilijalaComponent implements OnInit, OnDestroy {

  displayedColumns = ['id', 'adresa', 'brojPultova', 'posedujeSef', 'banka', 'actions'];
  dataSource!: MatTableDataSource<Filijala>;
  subscription!: Subscription;

  constructor(private filijalaService: FilijalaService,
              private dialog: MatDialog) {}

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData();
  }

 public loadData() {
    this.subscription = this.filijalaService.getAllFilijalas().subscribe(
    data => {
      this.dataSource = new MatTableDataSource(data);
    }
    ),
    (error: Error) => {
      console.log(error.name + ' ' + error.message);
    }
 }

 public openDialog(flag: number, id?: number, adresa?: string, brojPultova?: number, posedujeSef?: boolean, banka?: Banka) : void {
    const dialogRef = this.dialog.open(FilijalaDialogComponent, {data: {id, adresa, brojPultova, posedujeSef, banka}})
    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(res => {
      if(res == 1) 
      {
        this.loadData();
      }
    })
 }

 public selectRow(row: any) {
  console.log("Odraditi ovo");
 }
}
