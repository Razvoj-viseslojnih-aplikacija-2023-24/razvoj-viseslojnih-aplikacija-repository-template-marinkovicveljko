import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
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

constructor(private bankaService: BankaService) { }

ngOnDestroy(): void {
  this.subscription.unsubscribe();
}

ngOnInit(): void {
this.loadData();
}

public loadData() {
this.subscription=this.bankaService.getAllBanks().subscribe(
  data => {
   this.dataSource = new MatTableDataSource(data);
  }
),
(error: Error) => {
  console.log(error.name + ' ' + error.message);
}
}

}
