/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KayhianMongoAccessTestModule } from '../../../test.module';
import { ClipExtractMySuffixComponent } from '../../../../../../main/webapp/app/entities/clip-extract-my-suffix/clip-extract-my-suffix.component';
import { ClipExtractMySuffixService } from '../../../../../../main/webapp/app/entities/clip-extract-my-suffix/clip-extract-my-suffix.service';
import { ClipExtractMySuffix } from '../../../../../../main/webapp/app/entities/clip-extract-my-suffix/clip-extract-my-suffix.model';

describe('Component Tests', () => {

    describe('ClipExtractMySuffix Management Component', () => {
        let comp: ClipExtractMySuffixComponent;
        let fixture: ComponentFixture<ClipExtractMySuffixComponent>;
        let service: ClipExtractMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KayhianMongoAccessTestModule],
                declarations: [ClipExtractMySuffixComponent],
                providers: [
                    ClipExtractMySuffixService
                ]
            })
            .overrideTemplate(ClipExtractMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClipExtractMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClipExtractMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ClipExtractMySuffix('123')],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.clipExtracts[0]).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
