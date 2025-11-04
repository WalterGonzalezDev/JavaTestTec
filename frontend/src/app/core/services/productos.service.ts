import { HttpClient, HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Producto, ProductoResponse } from '../models/producto.interface';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductosService {
  private apiUrl = `${environment.apiUrl}/productos`;

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

  getProductos(page: number = 0, size: number = 10): Observable<ProductoResponse> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<ProductoResponse>(this.apiUrl, { params, headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }

  getProductoById(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() })
      .pipe(catchError(this.handleError));
  }
}