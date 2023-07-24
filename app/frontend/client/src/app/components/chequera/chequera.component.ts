import { Component } from '@angular/core';
import { ChequeraService } from 'src/app/services/chequera.service';
import { chequeraModel } from 'src/app/models/chequera';

@Component({
  selector: 'app-chequera',
  templateUrl: './chequera.component.html',
  styleUrls: ['./chequera.component.css']
})
export class ChequeraComponent {
  chequeraModel : chequeraModel = new chequeraModel();  

  visibleTable = false;
  visibleCreate = false;
  visibleUpdate = false;
  visibleTableCancelada = false;
  rows : any = [];
  canceladas : any = [];
  
  constructor(private chequeraService : ChequeraService){ }

  ngOnInit() {
    this.read();
  }

  read() {
    this.showTable();
    this.chequeraService.getChequeras().subscribe((res:any) =>{ 
      console.log(res);
      this.rows = []
      this.canceladas = []
      for(let row of res){
        this.chequeraModel = JSON.parse(JSON.stringify(row))
        this.chequeraModel.fecha_emision = this.chequeraModel.fecha_emision.substring(0,10)
        if(this.chequeraModel.estado == "A"){
          this.rows.push(this.chequeraModel)
        }else{
          this.canceladas.push(this.chequeraModel)
        }
      }
      if(this.canceladas.length != 0){
        this.visibleTableCancelada = true;
      }
    })
  }

  create(fecha_emision:any, cantidad:any, id_cuenta:any){
    if(fecha_emision.value == "" || cantidad.value == "" || id_cuenta.value == ""){
      alert("Todos los campos deben estar llenos");
      return;
    }
    if(cantidad.value.includes(".") || cantidad.value.includes("-") || cantidad.value.includes("+") || cantidad.value.includes("e")){
      alert("La cantidad no puede contener símbolos");
      return;
    }
    let monto = parseFloat(cantidad.value);
    if(monto < 0){
      alert("El saldo no puede ser negativo");
      return;
    }
    if(Number.isNaN(monto)){
      alert("El saldo debe ser un numero");
      return;
    }
    if(id_cuenta.value.includes(".") || id_cuenta.value.includes("-") || id_cuenta.value.includes("+") || id_cuenta.value.includes("e")){
      alert("El id de la cuenta no puede contener símbolos");
      return;
    }
    let id = parseInt(id_cuenta.value);
    if(Number.isNaN(id)){
      alert("El id de la cuenta no puede ser negativo");
      return;
    }

    this.chequeraModel = new chequeraModel();
    this.chequeraModel.fecha_emision = fecha_emision.value;
    this.chequeraModel.cantidad = cantidad.value;
    this.chequeraModel.id_cuenta = id_cuenta.value;
    this.chequeraModel.estado = "A";
    this.chequeraService.saveChequera(this.chequeraModel).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al crear chequera: " + error.message);
    })
  }

  update(cantidad:any){
    if(cantidad.value == ""){
      alert("Todos los campos deben estar llenos");
      return;
    }
    if(cantidad.value.includes(".") || cantidad.value.includes("-") || cantidad.value.includes("+") || cantidad.value.includes("e")){
      alert("La cantidad no puede contener símbolos");
      return;
    }
    let monto = parseFloat(cantidad.value);
    console.log(monto);
    if(monto < 0){
      alert("La cantidad no puede ser negativo");
      return;
    }
    if(Number.isNaN(monto)){
      alert("La cantidad debe ser un numero");
      return;
    }

    this.chequeraModel.cantidad = this.chequeraModel.cantidad + monto;
    this.chequeraService.updateChequera(this.chequeraModel).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al actualizar cuenta: " + error.message);
    })
    
  }

  delete(id:number){
    let id_chequera = this.rows[id].id_chequera;
    this.chequeraService.deleteChequera(id_chequera).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al eliminar cuenta: " + error.message);
      this.read();
    })
  }

  allFalse(){
    this.visibleCreate = false;
    this.visibleTable = false;
    this.visibleUpdate = false;
    this.visibleTableCancelada = false;
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
    this.chequeraModel = this.rows[id];
  }
  
}