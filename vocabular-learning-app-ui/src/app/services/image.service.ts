import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from '../app-constants';
import { IndividualResponse } from '../models/individual-response';
import { Image } from '../models/image';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  private imageAPIURL: string;

  constructor(private http: HttpClient, private appConstants: AppConstants) {
    this.imageAPIURL = appConstants.BASE_URL + "images/"
  }

  uploadImage(imageFormData: FormData, verbId:any):Observable<IndividualResponse<any>>{
    return this.http.post<IndividualResponse<any>>(this.imageAPIURL + verbId, imageFormData);
  }

  getImage(verbId:any, imageName:string):Observable<Image>{
    const params = new HttpParams()
    .set('verbId', verbId)
    .set('imageName', imageName)
    return this.http.get<Image>(this.imageAPIURL, {params});
  }

  deleteImage(verbId:any):Observable<IndividualResponse<Image>>{
    return this.http.delete<IndividualResponse<Image>>(this.imageAPIURL + verbId);
  }

}
