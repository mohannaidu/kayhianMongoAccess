/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { KayhianMongoAccessTestModule } from '../../../test.module';
import { ClipExtractMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/clip-extract-my-suffix/clip-extract-my-suffix-delete-dialog.component';
import { ClipExtractMySuffixService } from '../../../../../../main/webapp/app/entities/clip-extract-my-suffix/clip-extract-my-suffix.service';

describe('Component Tests', () => {

    describe('ClipExtractMySuffix Management Delete Component', () => {
        let comp: ClipExtractMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<ClipExtractMySuffixDeleteDialogComponent>;
        let service: ClipExtractMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KayhianMongoAccessTestModule],
                declarations: [ClipExtractMySuffixDeleteDialogComponent],
                providers: [
                    ClipExtractMySuffixService
                ]
            })
            .overrideTemplate(ClipExtractMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClipExtractMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClipExtractMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete('123');
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith('123');
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
