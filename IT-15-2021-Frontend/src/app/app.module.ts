import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { MatButtonModule } from '@angular/material/button'
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatSnackBarModule } from '@angular/material/snack-bar'
import { MatDialogModule } from '@angular/material/dialog'
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatInputModule } from '@angular/material/input';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './utility/home/home.component';
import { AboutComponent } from './utility/about/about.component';
import { AuthorComponent } from './utility/author/author.component';
import { VoziloComponent } from './components/primer-components/vozilo/vozilo.component';
import { AutomobilComponent } from './components/primer-components/automobil/automobil.component';
import { BankaComponent } from './main/banka/banka.component';
import { FilijalaComponent } from './main/filijala/filijala.component';
import { KorisniciComponent } from './main/korisnici/korisnici.component';
import { UslugaComponent } from './main/usluga/usluga.component';
import { HttpClientModule } from '@angular/common/http';
import { BankaDialogComponent } from './dialogs/banka-dialog/banka-dialog.component';
import { KorisniciDialogComponent } from './dialogs/korisnici-dialog/korisnici-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    BankaComponent,
    FilijalaComponent,
    KorisniciComponent,
    UslugaComponent,
    VoziloComponent,
    AutomobilComponent,
    BankaDialogComponent,
    KorisniciDialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    MatGridListModule,
    MatExpansionModule,
    MatTableModule,
    MatToolbarModule,
    HttpClientModule,
    MatSnackBarModule,
    MatDialogModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
