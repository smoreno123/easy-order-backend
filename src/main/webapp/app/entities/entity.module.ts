import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'item-pedido',
        loadChildren: () => import('./item-pedido/item-pedido.module').then(m => m.EasyorderBackendItemPedidoModule)
      },
      {
        path: 'pedido',
        loadChildren: () => import('./pedido/pedido.module').then(m => m.EasyorderBackendPedidoModule)
      },
      {
        path: 'ingrediente',
        loadChildren: () => import('./ingrediente/ingrediente.module').then(m => m.EasyorderBackendIngredienteModule)
      },
      {
        path: 'mesa',
        loadChildren: () => import('./mesa/mesa.module').then(m => m.EasyorderBackendMesaModule)
      },
      {
        path: 'valoracion',
        loadChildren: () => import('./valoracion/valoracion.module').then(m => m.EasyorderBackendValoracionModule)
      },
      {
        path: 'tipo-cocina',
        loadChildren: () => import('./tipo-cocina/tipo-cocina.module').then(m => m.EasyorderBackendTipoCocinaModule)
      },
      {
        path: 'pago-pedido',
        loadChildren: () => import('./pago-pedido/pago-pedido.module').then(m => m.EasyorderBackendPagoPedidoModule)
      },
      {
        path: 'tipo-item-pedido',
        loadChildren: () => import('./tipo-item-pedido/tipo-item-pedido.module').then(m => m.EasyorderBackendTipoItemPedidoModule)
      },
      {
        path: 'menu',
        loadChildren: () => import('./menu/menu.module').then(m => m.EasyorderBackendMenuModule)
      },
      {
        path: 'status-item-pedido',
        loadChildren: () => import('./status-item-pedido/status-item-pedido.module').then(m => m.EasyorderBackendStatusItemPedidoModule)
      },
      {
        path: 'comensal',
        loadChildren: () => import('./comensal/comensal.module').then(m => m.EasyorderBackendComensalModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EasyorderBackendEntityModule {}
