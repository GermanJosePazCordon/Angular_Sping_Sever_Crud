import { Component } from '@angular/core';

import { CuentaService } from 'src/app/services/cuenta.service';
import { cuentaModel } from 'src/app/models/cuenta';

@Component({
  selector: 'app-cuenta',
  templateUrl: './cuenta.component.html',
  styleUrls: ['./cuenta.component.css']
})
export class CuentaComponent {
  cuentaModel : cuentaModel = new cuentaModel();  

  visibleTable = false;
  visibleCreate = false;
  visibleUpdate = false;
  visibleTableCancelada = false;
  rows : any = [];
  canceladas : any = [];
  
  constructor(private cuentaService : CuentaService){ }

  ngOnInit() {
    this.read();
  }

  read() {
    this.showTable();
    this.cuentaService.getCuentas().subscribe((res:any) =>{ 
      console.log(res);
      this.rows = []
      this.canceladas = []
      for(let row of res){
        this.cuentaModel = JSON.parse(JSON.stringify(row))
        this.cuentaModel.fecha_creacion = this.cuentaModel.fecha_creacion.substring(0,10)
        if(this.cuentaModel.estado == "A"){
          this.rows.push(this.cuentaModel)
        }else{
          this.canceladas.push(this.cuentaModel)
        }
        if(this.canceladas.length != 0){
          this.visibleTableCancelada = true;
        }
      }
    })
  }

  create(fecha_creacion:any, saldo:any, tipo_cuenta:any, id_cliente:any){
    if(fecha_creacion.value == "" || saldo.value == "" || tipo_cuenta.value == "" || id_cliente.value == ""){
      alert("Todos los campos deben estar llenos");
      return;
    }
    let monto = parseFloat(saldo.value);
    if(monto < 0){
      alert("El saldo no puede ser negativo");
      return;
    }
    if(Number.isNaN(monto)){
      alert("El saldo debe ser un numero");
      return;
    }
    let id = parseInt(id_cliente.value);
    if(id_cliente.value.includes(".") || id_cliente.value.includes("-") || id_cliente.value.includes("+") || id_cliente.value.includes("e")){
      alert("El dpi no puede contener símbolos");
      return;
    }
    if(Number.isNaN(id)){
      alert("El dpi no puede ser negativo");
      return;
    }

    this.cuentaModel = new cuentaModel();
    this.cuentaModel.fecha_creacion = fecha_creacion.value;
    this.cuentaModel.estado = "A";
    this.cuentaModel.saldo = saldo.value;
    this.cuentaModel.tipo_cuenta = tipo_cuenta.value;
    this.cuentaModel.id_cliente = id_cliente.value;
    this.cuentaService.saveCuenta(this.cuentaModel).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al crear cuenta: " + error.message);
    })
  }

  update(saldo:any, ){
    if( saldo.value == ""){
      alert("Todos los campos deben estar llenos");
      return;
    }
    let monto = parseFloat(saldo.value);
    console.log(monto);
    if(Number.isNaN(monto)){
      alert("El saldo debe ser un numero");
      return;
    }
    this.cuentaModel.saldo = this.cuentaModel.saldo + monto ;
    this.cuentaService.updateCuenta(this.cuentaModel).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al actualizar cuenta: " + error.message);
    })
    
  }

  delete(id:number){
    let id_cuenta = this.rows[id].id_cuenta;
    this.cuentaService.deleteCuenta(id_cuenta).subscribe((res:any) =>{
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
    this.cuentaModel = this.rows[id]
  }
}