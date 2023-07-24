import { Component } from '@angular/core';
import { ChequeService } from 'src/app/services/cheque.service';
import { chequeModel } from 'src/app/models/cheque';

@Component({
  selector: 'app-cheque',
  templateUrl: './cheque.component.html',
  styleUrls: ['./cheque.component.css']
})
export class ChequeComponent {
  chequeModel : chequeModel = new chequeModel();  

  visibleTable = false;
  visibleCreate = false;
  visibleUpdate = false;
  visibleTableCancelada = false;
  visibleTableUsado = false;
  rows : any = [];
  canceladas : any = [];
  usados : any = [];
  
  constructor(private chequeService : ChequeService){ }

  ngOnInit() {
    this.read();
  }

  read() {
    this.showTable();
    this.chequeService.getCheques().subscribe((res:any) =>{ 
      console.log(res);
      this.rows = []
      this.canceladas = []
      this.usados = []
      for(let row of res){
        this.chequeModel = JSON.parse(JSON.stringify(row))
        this.chequeModel.fecha_emision = this.chequeModel.fecha_emision.substring(0,10)
        this.chequeModel.fecha_recibido = this.chequeModel.fecha_recibido.substring(0,10)
        if(this.chequeModel.fecha_recibido == "1999-01-01"){
          this.chequeModel.fecha_recibido = "Cheque no recibido"
        }
        if(this.chequeModel.estado == "A"){
          this.rows.push(this.chequeModel)
        }else if (this.chequeModel.estado == "U"){
          this.usados.push(this.chequeModel)
        }
        else{
          this.canceladas.push(this.chequeModel)
        }
      }
      if(this.canceladas.length != 0){
        this.visibleTableCancelada = true;
      }
      if(this.usados.length != 0){
        this.visibleTableUsado = true;
      }
    })
  }

  create(fecha_emision:any, monto:any, lugar:any, beneficiario:any, id_chequera:any){
    if(fecha_emision.value == "" || monto.value == ""  || lugar.value == "" || beneficiario.value == "" || id_chequera.value == ""){
      alert("Todos los campos deben estar llenos");
      return;
    }
    let saldo = parseFloat(monto.value);
    console.log(saldo);
    if(saldo < 0){
      alert("El saldo no puede ser negativo");
      return;
    }
    if(Number.isNaN(saldo)){
      alert("El saldo debe ser un número");
      return;
    }
    if(id_chequera.value.includes(".") || id_chequera.value.includes("-") || id_chequera.value.includes("+") || id_chequera.value.includes("e")){
      alert("La id de la chequera no puede contener símbolos");
      return;
    }
    let id = parseInt(id_chequera.value);
    if(Number.isNaN(id)){
      alert("El id de la chequera no puede ser negativo");
      return;
    }

    this.chequeModel = new chequeModel();
    this.chequeModel.fecha_emision = fecha_emision.value;
    this.chequeModel.fecha_recibido = "1999-01-01";
    this.chequeModel.monto = monto.value;
    this.chequeModel.lugar = lugar.value;
    this.chequeModel.beneficiario = beneficiario.value;
    this.chequeModel.id_chequera = id_chequera.value;
    this.chequeModel.estado = "A";
    this.chequeService.saveCheque(this.chequeModel).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al crear cheque: " + error.message);
    })
  }

  update(monto:any, lugar:any, beneficiario:any){
    if(monto.value == "" || lugar.value == "" || beneficiario.value == ""){
      alert("Todos los campos deben estar llenos");
      return;
    }
    let saldo = parseFloat(monto.value);
    console.log(saldo);
    if(saldo < 0){
      alert("El monto no puede ser negativo");
      return;
    }
    if(Number.isNaN(saldo)){
      alert("El monto debe ser un numero");
      return;
    }
    this.chequeModel.monto = monto.value;
    this.chequeModel.lugar = lugar.value;
    this.chequeModel.beneficiario = beneficiario.value;
    this.chequeService.updateCheque(this.chequeModel).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al actualizar cheque: " + error.message);
    })
  }

  usar(id: number){
    this.chequeModel = this.rows[id];
    this.chequeService.usarCheque(this.chequeModel.id_cheque).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al usar cheque: " + error.message);
    })
  }

  delete(id:number){
    let id_cheque = this.rows[id].id_cheque;
    this.chequeService.deleteCheque(id_cheque).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al eliminar: " + error.message);
    })
  }

  allFalse(){
    this.visibleCreate = false;
    this.visibleTable = false;
    this.visibleUpdate = false;
    this.visibleTableCancelada = false;
    this.visibleTableUsado = false;
  }

  showTable(){
    this.allFalse();
    this.visibleTable = true;
  }

  showCreate(){
    this.allFalse();
    this.visibleCreate = true;
  }

  showUpdate(id:number){
    this.allFalse();
    this.visibleUpdate = true;
    this.chequeModel = this.rows[id];
  }
  
}