import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from '../app-constants';
import { IndividualResponse } from '../models/individual-response';
import { ListResponse } from '../models/list-response';
import { Verb } from '../models/verb';
import { VerbSearchRequest } from '../models/verb-search-request';

@Injectable({
  providedIn: 'root'
})
export class VerbService {

  private verbsManagementURL: string;

  constructor(private http: HttpClient, private appConstants: AppConstants) {
    this.verbsManagementURL = appConstants.BASE_URL + "VerbsLearning/"
  }

  getVerbsList(verbSearchRequest: VerbSearchRequest): Observable<ListResponse> {
    return this.http.post<ListResponse>(this.verbsManagementURL, verbSearchRequest);
  }

  getVerbDetails(verbId: number): Observable<IndividualResponse> {
    return this.http.get<IndividualResponse>(this.verbsManagementURL + verbId);
  }

  updateVerb(updatedVerb: Verb): Observable<IndividualResponse> {
    return this.http.put<IndividualResponse>(this.verbsManagementURL, updatedVerb);
  }

  saveVerbs(verbs:Verb[]): Observable<IndividualResponse> {
    return this.http.post<IndividualResponse>(this.verbsManagementURL+"verbs", verbs);
  }

}
