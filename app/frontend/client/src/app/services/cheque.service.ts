import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class ChequeService {

  constructor(private httpClient: HttpClient) { }
  url = environment.apiURL + '/cheques';

  getCheques(){
    return this.httpClient.get(this.url);
  }

  saveCheque(cheque:any){
    console.log(cheque);
    return this.httpClient.post(this.url, cheque);
  }

  updateCheque(cheque:any){
    console.log("update: ",cheque);
    return this.httpClient.put(this.url, cheque);
  }

  usarCheque(id:number){
    console.log(this.url + '/usar/' + id);
    return this.httpClient.get(this.url + '/usar/' + id);
  }

  deleteCheque(id:number){
    console.log(this.url + '/' + id);
    return this.httpClient.delete(this.url + '/' + id);
  }
}
