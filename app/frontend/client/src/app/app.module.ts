import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ClienteComponent } from './components/cliente/cliente.component';
import { CuentaComponent } from './components/cuenta/cuenta.component';
import { HomeComponent } from './components/home/home.component';
import { ChequeraComponent } from './components/chequera/chequera.component';
import { ChequeComponent } from './components/cheque/cheque.component';

@NgModule({
  declarations: [
    AppComponent,
    ClienteComponent,
    CuentaComponent,
    HomeComponent,
    ChequeraComponent,
    ChequeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
