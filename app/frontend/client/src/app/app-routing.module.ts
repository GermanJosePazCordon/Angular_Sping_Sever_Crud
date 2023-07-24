import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ClienteComponent } from './components/cliente/cliente.component';
import { CuentaComponent } from './components/cuenta/cuenta.component';
import { HomeComponent } from './components/home/home.component';
import { ChequeraComponent } from './components/chequera/chequera.component';
import { ChequeComponent } from './components/cheque/cheque.component';

const routes: Routes = [
  {
    path:'home',
    component: HomeComponent
  },
  {
    path:'cliente',
    component: ClienteComponent
  },
  {
    path:'cuenta',
    component: CuentaComponent
  },
  {
    path:'chequera',
    component: ChequeraComponent
  },
  {
    path:'cheque',
    component: ChequeComponent
  },
  {
    path: '', 
    redirectTo: '/home', 
    pathMatch:'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
