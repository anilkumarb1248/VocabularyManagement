import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from '../app-constants';
import { IndividualResponse } from '../models/individual-response';
import { ListResponse } from '../models/list-response';
import { Word } from '../models/word';
import { WordSearchRequest } from '../models/word-search-request';

@Injectable({
  providedIn: 'root'
})
export class WordService {

  private wordsURL: string;

  constructor(private http: HttpClient, private appConstants: AppConstants) {
    this.wordsURL = appConstants.BASE_URL + "words/"
  }

  getAllWords(wordSearchRequest: WordSearchRequest): Observable<ListResponse<Word>> {
    return this.http.post<ListResponse<Word>>(this.wordsURL + "all", wordSearchRequest);
  }

  getWordDetails(wordId: number, searchWord: string): Observable<IndividualResponse<Word>> {

    const params = new HttpParams()
    .set('wordId', wordId)
    .set('searchWord', searchWord);

    return this.http.get<IndividualResponse<Word>>(this.wordsURL, {params});
  }

  insertWord(word: Word): Observable<IndividualResponse<Word>> {
    return this.http.post<IndividualResponse<Word>>(this.wordsURL, word);
  }

  updateWord(updatedWord: Word): Observable<IndividualResponse<Word>> {
    return this.http.put<IndividualResponse<Word>>(this.wordsURL, updatedWord);
  }

  deleteWord(wordId: number, searchWord: string): Observable<IndividualResponse<Word>> {

    const params = new HttpParams()
    .set('wordId', wordId)
    .set('searchWord', searchWord);

    return this.http.delete<IndividualResponse<Word>>(this.wordsURL, {params});
  }
}
