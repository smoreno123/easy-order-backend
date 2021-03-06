import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EasyorderBackendTestModule } from '../../../test.module';
import { TipoCocinaUpdateComponent } from 'app/entities/tipo-cocina/tipo-cocina-update.component';
import { TipoCocinaService } from 'app/entities/tipo-cocina/tipo-cocina.service';
import { TipoCocina } from 'app/shared/model/tipo-cocina.model';

describe('Component Tests', () => {
  describe('TipoCocina Management Update Component', () => {
    let comp: TipoCocinaUpdateComponent;
    let fixture: ComponentFixture<TipoCocinaUpdateComponent>;
    let service: TipoCocinaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EasyorderBackendTestModule],
        declarations: [TipoCocinaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoCocinaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoCocinaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoCocinaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoCocina(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoCocina();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
