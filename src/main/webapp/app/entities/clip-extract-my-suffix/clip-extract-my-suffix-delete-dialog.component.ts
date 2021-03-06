import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ClipExtractMySuffix } from './clip-extract-my-suffix.model';
import { ClipExtractMySuffixPopupService } from './clip-extract-my-suffix-popup.service';
import { ClipExtractMySuffixService } from './clip-extract-my-suffix.service';

@Component({
    selector: 'jhi-clip-extract-my-suffix-delete-dialog',
    templateUrl: './clip-extract-my-suffix-delete-dialog.component.html'
})
export class ClipExtractMySuffixDeleteDialogComponent {

    clipExtract: ClipExtractMySuffix;

    constructor(
        private clipExtractService: ClipExtractMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.clipExtractService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clipExtractListModification',
                content: 'Deleted an clipExtract'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clip-extract-my-suffix-delete-popup',
    template: ''
})
export class ClipExtractMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clipExtractPopupService: ClipExtractMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.clipExtractPopupService
                .open(ClipExtractMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
