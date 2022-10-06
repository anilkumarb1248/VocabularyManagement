import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from '../app-constants';

@Injectable({
  providedIn: 'root'
})
export class ExcelAPIService {

  private excelURL: string;

  constructor(private http: HttpClient, private appConstants: AppConstants) {
    this.excelURL = appConstants.BASE_URL + "excel/"
  }

  exportVerbs(): Observable<Blob> {
    return this.http.get<Blob>(this.excelURL + "verbs");
  }
}
