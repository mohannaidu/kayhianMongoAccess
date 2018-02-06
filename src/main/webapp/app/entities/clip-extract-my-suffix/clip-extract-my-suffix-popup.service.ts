import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ClipExtractMySuffix } from './clip-extract-my-suffix.model';
import { ClipExtractMySuffixService } from './clip-extract-my-suffix.service';

@Injectable()
export class ClipExtractMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private clipExtractService: ClipExtractMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.clipExtractService.find(id)
                    .subscribe((clipExtractResponse: HttpResponse<ClipExtractMySuffix>) => {
                        const clipExtract: ClipExtractMySuffix = clipExtractResponse.body;
                        clipExtract.createddate = this.datePipe
                            .transform(clipExtract.createddate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.clipExtractModalRef(component, clipExtract);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.clipExtractModalRef(component, new ClipExtractMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    clipExtractModalRef(component: Component, clipExtract: ClipExtractMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clipExtract = clipExtract;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
