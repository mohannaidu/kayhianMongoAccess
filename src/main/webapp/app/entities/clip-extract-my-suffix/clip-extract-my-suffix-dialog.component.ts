import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ClipExtractMySuffix } from './clip-extract-my-suffix.model';
import { ClipExtractMySuffixPopupService } from './clip-extract-my-suffix-popup.service';
import { ClipExtractMySuffixService } from './clip-extract-my-suffix.service';

@Component({
    selector: 'jhi-clip-extract-my-suffix-dialog',
    templateUrl: './clip-extract-my-suffix-dialog.component.html'
})
export class ClipExtractMySuffixDialogComponent implements OnInit {

    clipExtract: ClipExtractMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private clipExtractService: ClipExtractMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clipExtract.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clipExtractService.update(this.clipExtract));
        } else {
            this.subscribeToSaveResponse(
                this.clipExtractService.create(this.clipExtract));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ClipExtractMySuffix>>) {
        result.subscribe((res: HttpResponse<ClipExtractMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ClipExtractMySuffix) {
        this.eventManager.broadcast({ name: 'clipExtractListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-clip-extract-my-suffix-popup',
    template: ''
})
export class ClipExtractMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clipExtractPopupService: ClipExtractMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.clipExtractPopupService
                    .open(ClipExtractMySuffixDialogComponent as Component, params['id']);
            } else {
                this.clipExtractPopupService
                    .open(ClipExtractMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
