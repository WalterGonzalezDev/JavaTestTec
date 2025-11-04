import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Inventario } from '../models/inventario.interface';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InventarioService {
  private apiUrl = `${environment.apiUrl}/inventario`;

  constructor(private http: HttpClient) { }

  // Justificación: Usar variable de entorno para la API Key permite cambiarla fácilmente según ambiente (dev, prod, etc.)
  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'x-api-key': environment.apiKey
    });
  }

  // Justificación: Manejo centralizado de errores mejora la robustez y facilita el mantenimiento.
  private handleError(error: any) {
    let msg = 'Error desconocido';
    if (error.error instanceof ErrorEvent) {
      msg = `Error de cliente: ${error.error.message}`;
    } else {
      msg = `Error de servidor: ${error.status} - ${error.message}`;
    }
    return throwError(msg);
  }

  getInventarioByProductoId(productoId: number): Observable<Inventario> {
    return this.http.get<Inventario>(`${this.apiUrl}/producto/${productoId}`, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }

  actualizarInventario(id: number, cantidad: number): Observable<Inventario> {
    return this.http.patch<Inventario>(`${this.apiUrl}/${id}`, { cantidad }, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }
}