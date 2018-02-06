/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { KayhianMongoAccessTestModule } from '../../../test.module';
import { ClipExtractMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/clip-extract-my-suffix/clip-extract-my-suffix-dialog.component';
import { ClipExtractMySuffixService } from '../../../../../../main/webapp/app/entities/clip-extract-my-suffix/clip-extract-my-suffix.service';
import { ClipExtractMySuffix } from '../../../../../../main/webapp/app/entities/clip-extract-my-suffix/clip-extract-my-suffix.model';

describe('Component Tests', () => {

    describe('ClipExtractMySuffix Management Dialog Component', () => {
        let comp: ClipExtractMySuffixDialogComponent;
        let fixture: ComponentFixture<ClipExtractMySuffixDialogComponent>;
        let service: ClipExtractMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KayhianMongoAccessTestModule],
                declarations: [ClipExtractMySuffixDialogComponent],
                providers: [
                    ClipExtractMySuffixService
                ]
            })
            .overrideTemplate(ClipExtractMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClipExtractMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClipExtractMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ClipExtractMySuffix('123');
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.clipExtract = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'clipExtractListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ClipExtractMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.clipExtract = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'clipExtractListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
