import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class CuentaService {
  constructor(private httpClient: HttpClient) { }
  url = environment.apiURL + '/cuentas';
  
  getCuentas(){
    return this.httpClient.get(this.url);
  }

  saveCuenta(cuenta:any){
    console.log(cuenta);
    return this.httpClient.post(this.url, cuenta);
  }

  updateCuenta(cuenta:any){
    console.log("update: ",cuenta);
    return this.httpClient.put(this.url, cuenta);
  }
    
  deleteCuenta(id:number){
    console.log(this.url + '/' + id);
    return this.httpClient.delete(this.url + '/' + id);
  }
}
