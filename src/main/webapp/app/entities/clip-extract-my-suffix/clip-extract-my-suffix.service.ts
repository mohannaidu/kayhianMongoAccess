import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ClipExtractMySuffix } from './clip-extract-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ClipExtractMySuffix>;

@Injectable()
export class ClipExtractMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/clip-extracts';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/clip-extracts';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(clipExtract: ClipExtractMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(clipExtract);
        return this.http.post<ClipExtractMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(clipExtract: ClipExtractMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(clipExtract);
        return this.http.put<ClipExtractMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ClipExtractMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ClipExtractMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<ClipExtractMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ClipExtractMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<ClipExtractMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<ClipExtractMySuffix[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ClipExtractMySuffix[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ClipExtractMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ClipExtractMySuffix[]>): HttpResponse<ClipExtractMySuffix[]> {
        const jsonResponse: ClipExtractMySuffix[] = res.body;
        const body: ClipExtractMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ClipExtractMySuffix.
     */
    private convertItemFromServer(clipExtract: ClipExtractMySuffix): ClipExtractMySuffix {
        const copy: ClipExtractMySuffix = Object.assign({}, clipExtract);
        copy.createddate = this.dateUtils
            .convertDateTimeFromServer(clipExtract.createddate);
        return copy;
    }

    /**
     * Convert a ClipExtractMySuffix to a JSON which can be sent to the server.
     */
    private convert(clipExtract: ClipExtractMySuffix): ClipExtractMySuffix {
        const copy: ClipExtractMySuffix = Object.assign({}, clipExtract);

        copy.createddate = this.dateUtils.toDate(clipExtract.createddate);
        return copy;
    }
}
