import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit{

public cliente: Cliente=new Cliente();
public titulo: string="Crear cliente";
constructor( private clienteService: ClienteService, 
  private router: Router, private activatedRoute: ActivatedRoute ){

}
ngOnInit(){
  this.cargarCliente();
}


cargarCliente():void{
this.activatedRoute.params.subscribe(params =>{
  let id= +params['id']
  if(id){
    this.clienteService.getCliente(id).subscribe((cliente) => this.cliente=cliente)
  }
})
}
update():void{

  this.clienteService.update(this.cliente)
  .subscribe(json =>{
    this.router.navigate(['/clientes'])
    Swal.fire('Cliente actualizado', `${json.mensaje}: ${json.cliente.nombre} `,'success')
  })
}


create():void{
 // console.log("clicked");
 // console.log(this.cliente);
/* this.clienteService.create(this.cliente)
 .subscribe(response =>this.router.navigate(['/clientes'])
 );*/
 this.clienteService.create(this.cliente)
 .subscribe(json =>{
  this.router.navigate(['/clientes'])
  Swal.fire('Nuevo cliente', `${json.mensaje}: ${json.cliente.nombre} `,'success')
 }
 );
}
}


