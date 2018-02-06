import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KayhianMongoAccessSharedModule } from '../../shared';
import {
    ClipExtractMySuffixService,
    ClipExtractMySuffixPopupService,
    ClipExtractMySuffixComponent,
    ClipExtractMySuffixDetailComponent,
    ClipExtractMySuffixDialogComponent,
    ClipExtractMySuffixPopupComponent,
    ClipExtractMySuffixDeletePopupComponent,
    ClipExtractMySuffixDeleteDialogComponent,
    clipExtractRoute,
    clipExtractPopupRoute,
    ClipExtractMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...clipExtractRoute,
    ...clipExtractPopupRoute,
];

@NgModule({
    imports: [
        KayhianMongoAccessSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ClipExtractMySuffixComponent,
        ClipExtractMySuffixDetailComponent,
        ClipExtractMySuffixDialogComponent,
        ClipExtractMySuffixDeleteDialogComponent,
        ClipExtractMySuffixPopupComponent,
        ClipExtractMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ClipExtractMySuffixComponent,
        ClipExtractMySuffixDialogComponent,
        ClipExtractMySuffixPopupComponent,
        ClipExtractMySuffixDeleteDialogComponent,
        ClipExtractMySuffixDeletePopupComponent,
    ],
    providers: [
        ClipExtractMySuffixService,
        ClipExtractMySuffixPopupService,
        ClipExtractMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KayhianMongoAccessClipExtractMySuffixModule {}
