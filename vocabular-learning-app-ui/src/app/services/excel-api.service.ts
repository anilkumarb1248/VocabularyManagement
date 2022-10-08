import { HttpClient, HttpHeaders } from '@angular/common/http';
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

  getExportVerbsUrl():string{
    return this.excelURL + "verbs";
  }

  exportVerbsToFile(){
    window.open(this.getExportVerbsUrl());
  }

  exportCurrentGridVerbs(ids:number[], order:string){
    window.open(this.getExportVerbsUrl() + "/ids?ids="+ids+"&order="+order);
  }

  exportVerbs(): Observable<any> {
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
          'Content-Type': 'application/json'
      }),
      responseType: 'blob' as 'json'
   }
    return this.http.get<any>(this.excelURL + "verbs", HTTPOptions);
  }
}
