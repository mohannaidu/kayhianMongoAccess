import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { KayhianMongoAccessClipExtractMySuffixModule } from './clip-extract-my-suffix/clip-extract-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        KayhianMongoAccessClipExtractMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KayhianMongoAccessEntityModule {}
