import { Component } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import Swal from 'sweetalert2';
import { tap } from 'rxjs';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent {
 // clientes:Cliente[] | undefined;
  clientes: any[] = [];

    constructor(private clienteService: ClienteService){}
    ngOnInit() {
       //codigo del tutorial
      /* let page = 0;
       this.clienteService.getClientes(page)
         .pipe(
           tap((response: any) => {
             (response.content as Cliente[]).forEach(cliente => {
               console.log(cliente.nombre);
             });
           })
         ).subscribe(response => this.clientes = response.content as Cliente[]
           );*/
      /*-------code sin el paginador---------------
      let page = 0;
 this.clienteService.getClientes(page).subscribe(
    response => {
      this.clientes = response;
    },
    error => {
      console.log('Error al obtener los clientes: ', error);
    }
  );*/
     // this.clientes=this.clienteService.getClientes();
      /* codigo que funcion sin el paginador*/
      this.clienteService.getClientes().subscribe(
      clientes => this.clientes=clientes
      );
     
      
      }
    
    

    delete(cliente: Cliente):void{
      const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
          confirmButton: 'btn btn-success',
          cancelButton: 'btn btn-danger'
        },
        buttonsStyling: false
      })
      
      swalWithBootstrapButtons.fire({
        title: 'Está seguro?',
        text: `Seguro que desea eliminar el cliente ${cliente.nombre}?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Si, eliminar!',
        cancelButtonText: 'No, cancelar!',
        reverseButtons: true
      }).then((result) => {
        if (result.isConfirmed) {
          this.clienteService.delete(cliente.id).subscribe(
              response =>{
                this.clientes=this.clientes?.filter(cli =>cli !==cliente)
                swalWithBootstrapButtons.fire(
                  'Cliente eliminado!',
                  `Cliente ${cliente.nombre} eliminado con éxito!`,
                  'success'
                )
              }
          )
         
        } 
      })
    }

}
