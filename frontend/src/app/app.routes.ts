import { Routes } from '@angular/router';

import { Routes } from '@angular/router';
import { ProductosListComponent } from './features/productos/components/productos-list.component';
import { ProductoDetalleComponent } from './features/productos/components/producto-detalle.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'productos',
    pathMatch: 'full'
  },
  {
    path: 'productos',
    component: ProductosListComponent
  },
  {
    path: 'productos/:id',
    component: ProductoDetalleComponent
  }
];
