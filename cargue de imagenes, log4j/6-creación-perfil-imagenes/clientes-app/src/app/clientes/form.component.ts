import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { tap } from 'rxjs';


@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit{

public cliente: Cliente=new Cliente;
public titulo: string="Crear cliente";
public errores: any [] | undefined;
isSubmitting = false;

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
  },
  err =>{
   this.errores=err.error.errors as string[];
   console.log(err.error.errors);
   console.log("Estoy desde el backend"+err.status);
  });
}
create():void{

 /*------primera
  console.log("clicked");
console.log(this.cliente);
 this.clienteService.create(this.cliente)
 .subscribe(response =>this.router.navigate(['/clientes'])
 );*/
 /*----------code tuturial--------------*/
 this.isSubmitting = true;
 this.clienteService.create(this.cliente)
 .subscribe(json =>{
  this.router.navigate(['/clientes'])
  Swal.fire('Nuevo cliente', `${json.mensaje}: ${json.cliente.nombre} `,'success')
  this.isSubmitting = false;
 },
 err =>{
  this.errores=err.error.errors as string[];
  console.log(err.error.errors);
  console.log("Estoy desde el backend"+err.status);
  this.isSubmitting = false;
 }
 );
}

}


