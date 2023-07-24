import { Component, OnInit } from '@angular/core';
import { ClienteService } from 'src/app/services/cliente.service';
import { ClienteModel } from 'src/app/models/cliente';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css']
})
export class ClienteComponent implements OnInit {

  clienteModel : ClienteModel = new ClienteModel();  

  visibleTable = false;
  visibleCreate = false;
  visibleUpdate = false;
  visibleTableCancelada = false;
  rows : any = [];
  canceladas : any = [];
  
  constructor(private clienteService : ClienteService){ }

  ngOnInit() {
    this.read();
  }

  read() {
    this.showTable();
    this.clienteService.getClientes().subscribe((res:any) =>{ 
      console.log(res);
      this.rows = []
      this.canceladas = []
      for(let row of res){
        this.clienteModel = JSON.parse(JSON.stringify(row))
        if(this.clienteModel.estado == "A"){
          this.rows.push(this.clienteModel)
        }else{
          this.canceladas.push(this.clienteModel)
        }
      }
      if(this.canceladas.length != 0){
        this.visibleTableCancelada = true;
      }
    })
  }

  create(dpi:any, nombre:any, telefono:any, genero:any, direccion:any){
    if(dpi.value == "" || nombre.value == "" || telefono.value == "" || genero.value == "" || direccion.value == ""){
      alert("Todos los campos deben estar llenos");
      return;
    }
    if(dpi.value.includes(".") || dpi.value.includes("-") || dpi.value.includes("+") || dpi.value.includes("e")){
      alert("El dpi no puede contener símbolos");
      return;
    }
    let id = parseInt(dpi.value);
    if(Number.isNaN(id)){
      alert("El dpi debe ser un numero");
      return;
    }

    this.clienteModel = new ClienteModel();
    this.clienteModel.dpi = dpi.value;
    this.clienteModel.nombre = nombre.value;
    this.clienteModel.telefono = telefono.value;
    this.clienteModel.direccion = direccion.value;
    this.clienteModel.genero = genero.value;
    this.clienteModel.estado = "A";
    this.clienteService.saveCliente(this.clienteModel).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al crear cliente: " + error.message);
    })
  }

  update(nombre:any, telefono:any, genero:any, direccion:any){
    if(nombre.value == "" || telefono.value == "" || genero.value == "" || direccion.value == ""){
      alert("Todos los campos deben estar llenos");
      return;
    }

    this.clienteModel.dpi;
    this.clienteModel.nombre = nombre.value;
    this.clienteModel.telefono = telefono.value;
    this.clienteModel.direccion = direccion.value;
    this.clienteModel.genero = genero.value;
    this.clienteService.updateCliente(this.clienteModel).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al actualizar cliente: " + error.message);
    })
    
  }

  delete(id:number){
    let dpi = this.rows[id].dpi;
    this.clienteService.deleteCliente(dpi).subscribe((res:any) =>{
      console.log(res);
      alert(res.msg);
      this.read();
    },
    error => {
      alert("Error al eliminar cliente: " + error.message);
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
    this.clienteModel = this.rows[id]
  }
}
