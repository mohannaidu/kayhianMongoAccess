import { BaseEntity } from './../../shared';

export class ClipExtractMySuffix implements BaseEntity {
    constructor(
        public id?: string,
        public title?: string,
        public createddate?: any,
        public url?: string,
        public type?: number,
        public description?: string,
        public language?: number,
        public processstage?: number,
    ) {
    }
}
