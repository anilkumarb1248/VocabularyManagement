import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ListResponse } from 'src/app/models/list-response';
import { Word } from 'src/app/models/word';
import { WordSearchRequest } from 'src/app/models/word-search-request';
import { WordService } from 'src/app/services/word.service';
import { Speaker } from 'src/app/Voice/speaker';

@Component({
  selector: 'app-all-words',
  templateUrl: './all-words.component.html',
  styleUrls: ['./all-words.component.scss']
})
export class AllWordsComponent implements OnInit {

  listResponse: ListResponse<Word> | undefined;
  words: Word[] = new Array();
  initialLoadedData: Word[] = new Array();
  isLoaded: boolean = false;

  searchInput: string = "";
  sortOrder: string = "ASC";
  recordsSize: string = "10";
  selectedLetter: string = "A";
  allLetters: string[] = [
    "All", "A", "B", "C", "D", "E", "F", "G", "H", "i",
    "J", "K", "L", "M", "N", "O", "P", "Q", "R",
    "S", "T", "U", "V", "W", "X", "Y", "Z"
  ]
  totalRecords: number = 0;
  showSpeakAllBtn: boolean = true;
  modalOptions:NgbModalOptions | undefined;

  isViewWordClicked:boolean = false;
  isAddWordClicked:boolean = false;

  constructor(    
    private wordService: WordService,
    private speaker: Speaker,
    private modalService: NgbModal
    ) { }

  ngOnInit(): void {
    this.loadWordsList();
  }

  geClass(index: number): string {
    if (index % 2 == 0) {
      return "table-info";
    } else {
      return "table-warning";
    }
  }

  loadWordsList() {
    this.isLoaded = false;
    let wordSearchRequest = new WordSearchRequest()
    wordSearchRequest.searchInput = this.searchInput;
    wordSearchRequest.sortOrder = this.sortOrder;
    wordSearchRequest.selectedLetter = this.selectedLetter;
    wordSearchRequest.pageSize = Number(this.recordsSize);
    wordSearchRequest.currentPage = 0;

    this.wordService.getAllWords(wordSearchRequest).subscribe(
      (data) => {
        this.listResponse = data;
        this.words = this.listResponse.values;
        this.initialLoadedData = this.listResponse.values;
        this.totalRecords = this.words.length;
        this.isLoaded = true;
      },
      (error) => {
        console.log(error);
        this.words = new Array();
        this.initialLoadedData = new Array();
        this.isLoaded = true;
      }
    );
  }

  speakAll() {
    let preparedData = "";
    for (let word of this.words) {
      preparedData = preparedData + word.word + " \n..";
    }
    this.showSpeakAllBtn = false;
    this.textToSpeak(preparedData);

  }

  public textToSpeak(text: string): void {
    this.speaker.speak(text);
  }

  stopSpeaking() {
    this.speaker.stopSpeaking();
    this.showSpeakAllBtn = true;
  }

  searchInLoadedData() {
    if (!this.searchInput) {
      if(this.words.length != this.initialLoadedData.length){
        this.words = this.initialLoadedData;
      }
    } else {
      let searchedWords: Word[] = [];
      this.searchInput = this.searchInput.toLowerCase().trim();
        for (let word of this.initialLoadedData) {
          if(word.word.toLowerCase().includes(this.searchInput) || word.word.toLowerCase()== this.searchInput){
            searchedWords.push(word);
          }
        }

      this.words = searchedWords;
    }
    this.totalRecords = this.words.length;
  }

  addWordsList(wordModal: any){
    this.isViewWordClicked = false;
    this.isAddWordClicked = true;

    this.modalService.open(wordModal, {backdrop:'static',size:'xl',fullscreen: 'xl'})

  }

}
