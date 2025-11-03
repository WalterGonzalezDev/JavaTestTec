import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ProductosService } from '../../../core/services/productos.service';
import { InventarioService } from '../../../core/services/inventario.service';
import { Producto } from '../../../core/models/producto.interface';
import { Inventario } from '../../../core/models/inventario.interface';

@Component({
  selector: 'app-producto-detalle',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mx-auto p-4">
      @if (loading) {
        <div class="flex justify-center">
          <span class="loading">Cargando detalles...</span>
        </div>
      } @else if (error) {
        <div class="alert alert-error">
          {{ error }}
        </div>
      } @else if (producto) {
        <div class="bg-white rounded-lg shadow-md p-6">
          <h2 class="text-2xl font-bold mb-4">{{ producto.nombre }}</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="producto-info">
              <p class="text-gray-600 mb-2">Categoría: {{ producto.categoria }}</p>
              <p class="text-gray-800 mb-4">{{ producto.descripcion }}</p>
              <p class="text-xl font-bold text-blue-600">
                Precio: ${{ producto.precio }}
              </p>
            </div>
            
            <div class="inventario-info">
              <h3 class="text-lg font-semibold mb-3">Información de Inventario</h3>
              @if (inventario) {
                <div>
                  <p class="mb-2">
                    Cantidad disponible: {{ inventario.cantidad }}
                  </p>
                  <p class="mb-4">
                    Ubicación: {{ inventario.ubicacion }}
                  </p>
                  <div class="flex items-center gap-4">
                    <button 
                      (click)="actualizarCantidad(inventario.cantidad - 1)"
                      [disabled]="inventario.cantidad <= 0"
                      class="btn btn-outline">
                      -
                    </button>
                    <span class="font-bold">{{ inventario.cantidad }}</span>
                    <button 
                      (click)="actualizarCantidad(inventario.cantidad + 1)"
                      class="btn btn-outline">
                      +
                    </button>
                  </div>
                </div>
              } @else {
                <p class="text-gray-500">
                  No hay información de inventario disponible
                </p>
              }
            </div>
          </div>
          
          <div class="mt-6">
            <button 
              (click)="volver()" 
              class="btn btn-secondary">
              Volver a la lista
            </button>
          </div>
        </div>
      }
    </div>
  `,
  styles: [`
    .loading {
      @apply animate-spin h-8 w-8 border-4 border-blue-500 rounded-full border-t-transparent;
    }
    .btn {
      @apply px-4 py-2 rounded-md transition-colors;
    }
    .btn-outline {
      @apply border border-blue-500 text-blue-500 hover:bg-blue-600 hover:text-white;
    }
    .btn-secondary {
      @apply bg-gray-500 text-white hover:bg-gray-600;
    }
    .alert-error {
      @apply bg-red-100 border-l-4 border-red-500 text-red-700 p-4;
    }
  `]
})
export class ProductoDetalleComponent implements OnInit {
  producto: Producto | null = null;
  inventario: Inventario | null = null;
  loading = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private productosService: ProductosService,
    private inventarioService: InventarioService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.cargarProducto(+id);
    } else {
      this.error = 'ID de producto no válido';
      this.loading = false;
    }
  }

  cargarProducto(id: number): void {
    this.productosService.getProductoById(id).subscribe({
      next: (producto) => {
        this.producto = producto;
        this.cargarInventario(id);
      },
      error: (error) => {
        this.error = 'Error al cargar el producto';
        this.loading = false;
        console.error('Error:', error);
      }
    });
  }

  cargarInventario(productoId: number): void {
    this.inventarioService.getInventarioByProductoId(productoId).subscribe({
      next: (inventario) => {
        this.inventario = inventario;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Error al cargar la información de inventario';
        this.loading = false;
        console.error('Error:', error);
      }
    });
  }

  actualizarCantidad(nuevaCantidad: number): void {
    if (!this.inventario) return;
    
    this.loading = true;
    this.inventarioService.actualizarInventario(this.inventario.id, nuevaCantidad)
      .subscribe({
        next: (inventarioActualizado) => {
          this.inventario = inventarioActualizado;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Error al actualizar el inventario';
          this.loading = false;
          console.error('Error:', error);
        }
      });
  }

  volver(): void {
    // Implementar navegación de regreso
  }
}