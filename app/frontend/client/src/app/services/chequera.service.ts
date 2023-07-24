import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class ChequeraService {
  constructor(private httpClient: HttpClient) { }
  url = environment.apiURL + '/chequeras';

  getChequeras(){
    return this.httpClient.get(this.url);
  }

  saveChequera(chequera:any){
    console.log(chequera);
    return this.httpClient.post(this.url, chequera);
  }

  updateChequera(chequera:any){
    console.log("update: ",chequera);
    return this.httpClient.put(this.url, chequera);
  }

  deleteChequera(id:number){
    console.log(this.url + '/' + id);
    return this.httpClient.delete(this.url + '/' + id);
  }
}
