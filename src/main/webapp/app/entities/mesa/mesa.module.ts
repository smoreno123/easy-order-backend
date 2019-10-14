import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EasyorderBackendSharedModule } from 'app/shared/shared.module';
import { MesaComponent } from './mesa.component';
import { MesaDetailComponent } from './mesa-detail.component';
import { MesaUpdateComponent } from './mesa-update.component';
import { MesaDeletePopupComponent, MesaDeleteDialogComponent } from './mesa-delete-dialog.component';
import { mesaRoute, mesaPopupRoute } from './mesa.route';

const ENTITY_STATES = [...mesaRoute, ...mesaPopupRoute];

@NgModule({
  imports: [EasyorderBackendSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [MesaComponent, MesaDetailComponent, MesaUpdateComponent, MesaDeleteDialogComponent, MesaDeletePopupComponent],
  entryComponents: [MesaDeleteDialogComponent]
})
export class EasyorderBackendMesaModule {}
