import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ClipExtractMySuffixComponent } from './clip-extract-my-suffix.component';
import { ClipExtractMySuffixDetailComponent } from './clip-extract-my-suffix-detail.component';
import { ClipExtractMySuffixPopupComponent } from './clip-extract-my-suffix-dialog.component';
import { ClipExtractMySuffixDeletePopupComponent } from './clip-extract-my-suffix-delete-dialog.component';

@Injectable()
export class ClipExtractMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const clipExtractRoute: Routes = [
    {
        path: 'clip-extract-my-suffix',
        component: ClipExtractMySuffixComponent,
        resolve: {
            'pagingParams': ClipExtractMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kayhianMongoAccessApp.clipExtract.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'clip-extract-my-suffix/:id',
        component: ClipExtractMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kayhianMongoAccessApp.clipExtract.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clipExtractPopupRoute: Routes = [
    {
        path: 'clip-extract-my-suffix-new',
        component: ClipExtractMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kayhianMongoAccessApp.clipExtract.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clip-extract-my-suffix/:id/edit',
        component: ClipExtractMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kayhianMongoAccessApp.clipExtract.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clip-extract-my-suffix/:id/delete',
        component: ClipExtractMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kayhianMongoAccessApp.clipExtract.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
