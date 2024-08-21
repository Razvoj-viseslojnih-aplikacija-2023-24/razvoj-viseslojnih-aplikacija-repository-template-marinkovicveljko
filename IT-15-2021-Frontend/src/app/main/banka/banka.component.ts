import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { BankaDialogComponent } from 'src/app/dialogs/banka-dialog/banka-dialog.component';
import { Banka } from 'src/app/models/banka';
import { BankaService } from 'src/app/services/banka.service';

@Component({
  selector: 'app-banka',
  templateUrl: './banka.component.html',
  styleUrls: ['./banka.component.css']
})

export class BankaComponent implements OnInit, OnDestroy {

  displayedColumns = ['id', 'naziv', 'kontakt', 'pib', 'actions']
  dataSource!: MatTableDataSource<Banka>;
  subscription!: Subscription;
  @ViewChild(MatSort, {static: false}) sort!: MatSort
  @ViewChild(MatPaginator, {static: false}) paginator!: MatPaginator

  constructor(private bankaService: BankaService,
    private dialog: MatDialog) { }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData();
  }

  public loadData() {
    this.subscription = this.bankaService.getAllBanks().subscribe(
      (data) => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      }
    ),
      (error: Error) => {
        console.log(error.name + ' ' + error.message);
      }
  }

  public openDialog(flag: number, id?: number, naziv?: string, kontakt?: string, pib?: number): void {
    const dialogRef = this.dialog.open(BankaDialogComponent, { data: { id, naziv, kontakt, pib } })

    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(res => {
      if (res == 1) {
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
