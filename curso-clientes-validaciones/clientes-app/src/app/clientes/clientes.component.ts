import { Component } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent {
 // clientes:Cliente[] | undefined;
  clientes: any[] = [];

    constructor(private clienteService: ClienteService){}
    ngOnInit(){
     // this.clientes=this.clienteService.getClientes();
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
