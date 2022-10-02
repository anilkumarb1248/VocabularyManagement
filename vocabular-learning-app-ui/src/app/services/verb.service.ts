import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from '../app-constants';
import { ListResponse } from '../models/list-response';
import { Verb } from '../models/verb';

@Injectable({
  providedIn: 'root'
})
export class VerbService {

  private verbsManagementURL: string;

  constructor(private http: HttpClient, private appConstants: AppConstants) {
    this.verbsManagementURL = appConstants.BASE_URL + "VerbsLearning/"
  }

  getVerbsList(): Observable<ListResponse> {
    return this.http.get<ListResponse>(this.verbsManagementURL);
  }

  getVerbsByPagination(pageNumber:number,pageSize:number, sortOrder:string, sortingBy:string): Observable<Verb[]> {

    const params = new HttpParams()
    .set('pageNumber', pageNumber.toString())
    .set('pageSize', pageSize.toString())
    .set('sortOrder', sortOrder)
    .set('sortingBy', sortingBy);

    return this.http.get<Verb[]>(this.verbsManagementURL + "getEmployeesByPagination",{params});
  }
}
