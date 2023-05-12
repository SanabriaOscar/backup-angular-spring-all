import { Component, OnInit } from '@angular/core';
import { Cliente } from '../cliente';
import { ClienteService } from '../cliente.service';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';

@Component({
  selector: 'detalle-cliente',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.css']
})
export class DetalleComponent implements OnInit{
  progreso: number = 0;
  cliente: Cliente | null = null;
  titulo: string = "Detalle del cliente";
  public fotoSeleccionada: File | null = null;
  constructor( private clienteService: ClienteService, private activatedRoute: ActivatedRoute){

  }
  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params =>{
      let id: number = Number(params.get('id'));
      if(id){
this.clienteService.getCliente(id).subscribe(cliente =>{
  this.cliente=cliente;
})
      }
    }
    );
  }

  seleccionarFoto(event : any){
this.fotoSeleccionada=event.target.files[0];
console.log(this.fotoSeleccionada)
if (this.fotoSeleccionada && this.fotoSeleccionada.type.indexOf('image') < 0) {
  Swal.fire('Error', 'El formato debe ser tipo imagen', 'error');
}

  }
//---------------------code del tutorial----------------------
  subirFoto() {
    if (!this.cliente || !this.fotoSeleccionada) {
      Swal.fire('Error', 'Selecciona una foto', 'error');
      return;
    }
  
    this.clienteService.subirFoto(this.fotoSeleccionada, this.cliente.id).subscribe(
      (cliente: any) => {
        this.cliente = cliente;
        Swal.fire('Éxito', 'La foto se ha subido con éxito', 'success');
      },
      (error: any) => {
        console.error(error);
        Swal.fire('Error', 'Error al subir la foto', 'error');
      }
    );
  }
  //barra de progreso por mejorara
  /*subirFoto() {
    if (!this.cliente || !this.fotoSeleccionada) {
      Swal.fire('Error', 'Selecciona una foto', 'error');
      return;
    }

    const formData = new FormData();
    formData.append('archivo', this.fotoSeleccionada);
    formData.append('id', this.cliente.id.toString());

    this.clienteService.subirFoto(this.fotoSeleccionada, this.cliente.id).subscribe(
      (event: any) => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progreso = Math.round((event.loaded / event.total) * 100);
        } else if (event.type === HttpEventType.Response) {
          this.cliente = event.body.cliente as Cliente;
          Swal.fire('Éxito', 'La foto se ha subido con éxito', 'success');
        }
      },
      (error: any) => {
        console.error(error);
        Swal.fire('Error', 'Error al subir la foto', 'error');
      }
    );
  }*/

}
