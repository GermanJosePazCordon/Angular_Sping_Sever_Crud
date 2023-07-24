import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  constructor(private httpClient: HttpClient) {}
  url = environment.apiURL + '/clientes';
  
  getClientes(){
    return this.httpClient.get(this.url);
  }

  saveCliente(cliente:any){
    console.log(cliente);
    return this.httpClient.post(this.url, cliente);
  }

  updateCliente(cliente:any){
    console.log("update: ",cliente);
    return this.httpClient.put(this.url, cliente);
  }
    
  deleteCliente(id:number){
    console.log(this.url + '/' + id);
    return this.httpClient.delete(this.url + '/' + id);
  }
}