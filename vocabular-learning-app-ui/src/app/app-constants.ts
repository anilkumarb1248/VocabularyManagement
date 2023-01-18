import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class AppConstants {
    // BASE_URL: string  = "http://localhost:2023/VocabularyLearning/";

    // BASE_URL: string  = "http://desktop-ghd0jav:2023/VocabularyLearning/"; // Not loading in Mobile
    // BASE_URL: string  = "http://127.0.0.1:2023/VocabularyLearning/"; // Not loading in Mobile
    BASE_URL: string  = "http://192.168.0.143:2023/VocabularyLearning/"; // Loading in Mobile
}
