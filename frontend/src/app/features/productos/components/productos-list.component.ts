import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductosService } from '../../../core/services/productos.service';
import { InventarioService } from '../../../core/services/inventario.service';
import { Producto } from '../../../core/models/producto.interface';

@Component({
  selector: 'app-productos-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mx-auto p-4">
      <h2 class="text-2xl font-bold mb-4">Lista de Productos</h2>
      
      @if (loading) {
        <div class="flex justify-center">
          <span class="loading">Cargando productos...</span>
        </div>
      } @else if (error) {
        <div class="alert alert-error">
          {{ error }}
        </div>
      } @else {
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          @for (producto of productos; track producto.id) {
            <div class="card">
              <div class="card-body">
                <h3 class="card-title">{{ producto.nombre }}</h3>
                <p>{{ producto.descripcion }}</p>
                <p class="font-bold">Precio: ${{ producto.precio }}</p>
                <button (click)="verDetalles(producto.id)" class="btn btn-primary">
                  Ver detalles
                </button>
              </div>
            </div>
          }
        </div>
        
        <div class="flex justify-center mt-4">
          <button 
            (click)="cambiarPagina(currentPage - 1)"
            [disabled]="currentPage === 0"
            class="btn btn-outline mr-2">
            Anterior
          </button>
          <span class="mx-4 py-2">
            Página {{ currentPage + 1 }} de {{ totalPages }}
          </span>
          <button 
            (click)="cambiarPagina(currentPage + 1)"
            [disabled]="currentPage >= totalPages - 1"
            class="btn btn-outline ml-2">
            Siguiente
          </button>
        </div>
      }
    </div>
  `,
  styles: [`
    .loading {
      @apply animate-spin h-8 w-8 border-4 border-blue-500 rounded-full border-t-transparent;
    }
    .card {
      @apply bg-white rounded-lg shadow-md overflow-hidden;
    }
    .card-body {
      @apply p-4;
    }
    .card-title {
      @apply text-xl font-semibold mb-2;
    }
    .btn {
      @apply px-4 py-2 rounded-md transition-colors;
    }
    .btn-primary {
      @apply bg-blue-500 text-white hover:bg-blue-600;
    }
    .btn-outline {
      @apply border border-blue-500 text-blue-500 hover:bg-blue-500 hover:text-white;
    }
    .alert-error {
      @apply bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-4;
    }
  `]
})
export class ProductosListComponent implements OnInit {
  productos: Producto[] = [];
  loading = true;
  error: string | null = null;
  currentPage = 0;
  totalPages = 0;
  itemsPerPage = 10;

  constructor(
    private productosService: ProductosService,
    private inventarioService: InventarioService
  ) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.loading = true;
    this.error = null;
    
    this.productosService.getProductos(this.currentPage, this.itemsPerPage)
      .subscribe({
        next: (response) => {
          this.productos = response.data;
          this.totalPages = response.meta.totalPages;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Error al cargar los productos. Por favor, intente nuevamente.';
          this.loading = false;
          console.error('Error:', error);
        }
      });
  }

  cambiarPagina(nuevaPagina: number): void {
    this.currentPage = nuevaPagina;
    this.cargarProductos();
  }

  verDetalles(id: number): void {
    // Implementar navegación al detalle del producto
  }
}