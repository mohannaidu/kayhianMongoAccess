import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ClipExtractMySuffix } from './clip-extract-my-suffix.model';
import { ClipExtractMySuffixService } from './clip-extract-my-suffix.service';

@Component({
    selector: 'jhi-clip-extract-my-suffix-detail',
    templateUrl: './clip-extract-my-suffix-detail.component.html'
})
export class ClipExtractMySuffixDetailComponent implements OnInit, OnDestroy {

    clipExtract: ClipExtractMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private clipExtractService: ClipExtractMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClipExtracts();
    }

    load(id) {
        this.clipExtractService.find(id)
            .subscribe((clipExtractResponse: HttpResponse<ClipExtractMySuffix>) => {
                this.clipExtract = clipExtractResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClipExtracts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clipExtractListModification',
            (response) => this.load(this.clipExtract.id)
        );
    }
}
