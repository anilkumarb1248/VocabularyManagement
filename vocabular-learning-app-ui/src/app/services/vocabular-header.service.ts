import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from '../app-constants';
import { IndividualResponse } from '../models/individual-response';
import { ListResponse } from '../models/list-response';
import { VocabularyHeader } from '../models/vocabulary-header';

@Injectable({
  providedIn: 'root'
})
export class VocabularHeaderService {

  private vocabularyHeaderURL: string;

  constructor(private http: HttpClient, private appConstants: AppConstants) {
    this.vocabularyHeaderURL = appConstants.BASE_URL + "VocabularyHeaders/"
  }

  getAllVocabularyHeaders(): Observable<ListResponse<VocabularyHeader>> {
    return this.http.get<ListResponse<VocabularyHeader>>(this.vocabularyHeaderURL);
  }

  getVocabularyHeaderDetails(headerId: number): Observable<IndividualResponse<VocabularyHeader>> {
    return this.http.get<IndividualResponse<VocabularyHeader>>(this.vocabularyHeaderURL + headerId);
  }

  addVocabularyHeader(vocabularyHeader: VocabularyHeader): Observable<IndividualResponse<VocabularyHeader>> {
    return this.http.post<IndividualResponse<VocabularyHeader>>(this.vocabularyHeaderURL, vocabularyHeader);
  }

  deleteVocabularyHeader(headerId:number): Observable<IndividualResponse<VocabularyHeader>> {
    return this.http.delete<IndividualResponse<VocabularyHeader>>(this.vocabularyHeaderURL + headerId);
  }

}
