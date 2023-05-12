import { Injectable } from '@angular/core';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente';
import { Observable, throwError } from 'rxjs';
import { of } from 'rxjs';
import { map, catchError, tap} from 'rxjs/operators';
import { HttpClient, HttpHandler, HttpHeaders} from '@angular/common/http';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private urlEndPoint:string='http://localhost:8080/api/clientes';
  private httpHeadres= new HttpHeaders({'Content-Type':'application/json'});

  constructor(private http: HttpClient, private router: Router) { }
 /* getClientes():Cliente[]{
    return CLIENTES;

  }*/
 /*-----------Codigo que funciona sin el paginador----------------------*/
 /* getClientes():Observable<Cliente[]>{
    //return of (CLIENTES);
    return this.http.get(this.urlEndPoint).pipe(
      map((response)=>response as Cliente[])
    );

  }*/
//---------codigo del tuturial-----------
 getClientes(page: number): Observable<any[]> {
    return this.http.get(this.urlEndPoint+'/page/'+page).pipe(
      tap((response: any) => {
        (response.content as Cliente[]).forEach(cliente => {
          console.log(cliente.nombre);
        });
      }),
      map((response: any) => {
        //let clientes = response as Cliente[];
       // return clientes.map(cliente => {
        (response.content as Cliente[]).forEach(cliente => {
          //cliente.nombre = cliente.nombre;
          return cliente;
        });
        return response;
      }),
      tap((response: any) => {
        (response.content as Cliente[]).forEach(cliente=> {
          console.log(cliente.nombre);
        });
      })
    );
  }
    
 /* create(cliente: Cliente): Observable<Cliente> {
    // return this.http.post<Cliente>(this.urlEndPoint,cliente,{headers:this.httpHeadres});
     return this.http.post<Cliente>(this.urlEndPoint,cliente,{headers:this.httpHeadres}).pipe(
      catchError(e=>{
        //this.router.navigate(['/clientes']);
        console.log(e.error.mensaje);
        Swal.fire('Error al crear al cliente ', e.error.error, "error");
        return throwError(e);

      })
    
     );
    }*/
    
    create(cliente: Cliente): Observable<any> {
    
      // Verificar si los campos están vacíos
     // if (!cliente.nombre || !cliente.apellido || !cliente.email) {
        // Mostrar mensaje de error con SweetAlert
       // Swal.fire('Error', 'Por favor completa todos los campos', 'error');
        // Devolver un Observable que emita un error
       // return throwError('Campos vacíos');
      //}
     /*--------------------code tutorial---------------*/ 
     return this.http.post<any>(this.urlEndPoint, cliente, { headers: this.httpHeadres }).pipe(
        catchError(e => {
          if(e.status==400){
            return throwError(e);
          }
          if(e.status==500){
            return throwError(e);
          }
          // this.router.navigate(['/clientes']);
          console.log(e.error.error);
          Swal.fire('Error al crear al cliente', e.error.error, 'error');
          return throwError(e);
        })
      );
    } 

 getCliente(id: any):Observable<Cliente>{
     // return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`);
      return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`).pipe(
        catchError(e=>{
          this.router.navigate(['/clientes']);
          console.log(e.error.mensaje);
          Swal.fire('Error al editar el cliente', e.error.mensaje, "error");
          return throwError(e);
        })
      );
    }

    update(cliente:Cliente):Observable<any>{
      //return this.http.put<Cliente>(`${this.urlEndPoint}/${cliente.id}`, cliente, {headers: this.httpHeadres});
      return this.http.put<any>(`${this.urlEndPoint}/${cliente.id}`, cliente, {headers: this.httpHeadres}).pipe(
        catchError(e=>{
            if(e.status==400){
              return throwError(e);
            }
          //this.router.navigate(['/clientes']);
          console.log(e.error.mensaje);
          Swal.fire('Error al actualizar al cliente ', e.error.mensaje, "error");
          return throwError(e);
  
        })
       );
    }
    delete(id:number):Observable<Cliente>{
    //  return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeadres});
      return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`,{headers: this.httpHeadres}).pipe(
        catchError(e=>{
          //this.router.navigate(['/clientes']);
          console.log(e.error.mensaje);
          Swal.fire('Error al eliminar al cliente ', e.error.mensaje, "error");
          return throwError(e);
  
        })
       );
      }
      subirFoto(archivo: File, id: number): Observable<Cliente> {
        const formData = new FormData();
        formData.append('archivo', archivo);
        formData.append('id', id.toString());
      
        return this.http.post(`${this.urlEndPoint}/upload`, formData).pipe(
          map((response: any) => response.cliente as Cliente),
          catchError(e => {
            console.log(e.error.mensaje);
            Swal.fire('Error al subir la imagen', e.error.mensaje, 'error');
            return throwError(e);
          })
        );
      }

    }


  

