import { Component, OnInit, ViewChild } from '@angular/core';
// import { Router } from '@angular/router';
// import { AgGridAngular } from 'ag-grid-angular';
import { ListResponse } from 'src/app/models/list-response';
import { Verb } from 'src/app/models/verb';
import { ExcelAPIService } from 'src/app/services/excel-api.service';
import { VerbService } from 'src/app/services/verb.service';
import { Speaker } from 'src/app/Voice/speaker';
import { saveAs } from 'file-saver';
import { VerbSearchRequest } from 'src/app/models/verb-search-request';
import {NgbModal, ModalDismissReasons, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-verbs-list',
  templateUrl: './verbs-list.component.html',
  styleUrls: ['./verbs-list.component.scss']
})
export class VerbsListComponent implements OnInit {

  listResponse: ListResponse | undefined;
  verbs: Verb[] = new Array();
  initialLoadedData: Verb[] = new Array();
  isLoaded: boolean = false;

  searchType: string = "anywhere";
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
  currentViewVerbId:number = 0;

  constructor(
    private verbService: VerbService,
    private speaker: Speaker,
    private excelAPIService: ExcelAPIService,
    private modalService: NgbModal
  ) { 

    this.modalOptions = {
      backdrop:'static', // by adding this, if we click anywhere out it will be closed
      size:'xl',
      fullscreen: 'xl'
    }

  }

  ngOnInit(): void {
    this.loadVerbsList();
    //You can select the voice using the indexex of getVoices()[1]
    // let voice = (speechSynthesis.getVoices()[3] || null);
    // this.speaker.setVoice(voice);
  }

  geClass(index: number): string {
    if (index % 2 == 0) {
      return "table-info";
    } else {
      return "table-warning";
    }
  }


  loadVerbsList() {
    this.isLoaded = false;
    let verbSearchRequest = new VerbSearchRequest()
    verbSearchRequest.searchType = this.searchType;
    verbSearchRequest.searchInput = this.searchInput;
    verbSearchRequest.sortOrder = this.sortOrder;
    verbSearchRequest.selectedLetter = this.selectedLetter;
    verbSearchRequest.pageSize = Number(this.recordsSize);
    verbSearchRequest.currentPage = 0;
    verbSearchRequest.learningStatus = "";
    verbSearchRequest.excludedChildren = true;

    this.verbService.getVerbsList(verbSearchRequest).subscribe(
      (data) => {
        this.listResponse = data;
        this.verbs = this.listResponse.values;
        this.initialLoadedData = this.verbs;
        this.totalRecords = this.verbs.length;
        this.isLoaded = true;
      },
      (error) => {
        console.log(error);
        this.isLoaded = true;
      }
    );
  }

  searchInLoadedData() {
    if (!this.searchInput) {
      if(this.verbs.length != this.initialLoadedData.length){
        this.verbs = this.initialLoadedData;
      }
    } else {
      let searchedVerbs: Verb[] = [];
      if (this.searchType == "starting") {
        for (let verb of this.verbs) {
          if(verb.baseForm.startsWith(this.searchInput) || verb.pastTenseForm.startsWith(this.searchInput) || verb.pastParticipleForm.startsWith(this.searchInput)){
            searchedVerbs.push(verb);
          }
        }
      } else if (this.searchType == "ending") {
        for (let verb of this.verbs) {
          if(verb.baseForm.endsWith(this.searchInput) || verb.pastTenseForm.endsWith(this.searchInput) || verb.pastParticipleForm.endsWith(this.searchInput)){
            searchedVerbs.push(verb);
          }
        }
      } else {
        for (let verb of this.verbs) {
          if(verb.baseForm.includes(this.searchInput) || verb.pastTenseForm.includes(this.searchInput) || verb.pastParticipleForm.includes(this.searchInput)){
            searchedVerbs.push(verb);
          }
        }
      }
      this.verbs = searchedVerbs;
    }
    this.totalRecords = this.verbs.length;
  }

  public textToSpeak(text: string): void {
    this.speaker.speak(text);
  }

  modifySearh() {
    this.loadVerbsList();
  }

  speakAll() {
    let preparedData = "";
    for (let verb of this.verbs) {
      preparedData = preparedData + this.prepareString(verb) + "\n";
    }
    this.showSpeakAllBtn = false;
    this.textToSpeak(preparedData);

  }

  stopSpeaking() {
    this.speaker.stopSpeaking();
    this.showSpeakAllBtn = true;
  }

  prepareString(verb: Verb): string {
    let data = verb.baseForm + ". " + verb.pastTenseForm + ". " + verb.pastParticipleForm + ". " + verb.thirdPersonBaseForm + ". " + verb.progressiveForm + ". ";


    return data;
  }

  exportVerbs(): void {
    this.excelAPIService.exportVerbsToFile();
    // this.excelAPIService.exportVerbs().subscribe(
    //   (response:any) => {
    //     console.log("We got the response");
    //     // var blob = new Blob([response._body], { type: 'application/vnd.ms-excel'});
    //     // var blob = new Blob([s2ab(atob(response))], { type: ''});
    //     // saveAs(blob, "verbs.xlsx");


    //     // if (window.navigator && window.navigator.msSaveOrOpenBlob) {
    //     //   window.navigator.msSaveOrOpenBlob(blob, fileName);
    //     //   this.blockedDocument = false;
    //     // } else {
    //     //   var a = document.createElement('a');
    //     //   a.href = URL.createObjectURL(blob);
    //     //   a.download = fileName;
    //     //   document.body.appendChild(a);
    //     //   a.click();
    //     //   document.body.removeChild(a);
    //     //   this.blockedDocument = false;
    //     // }
    //   },
    //   err => {
    //     alert("Error while downloading. File Not Found on the Server");
    //   }
    //   );
  }

  exportCurrentGridVerbs(){
    let ids = new Array();
    for (let verb of this.verbs) {
      ids.push(verb.verbId);
    }
    this.excelAPIService.exportCurrentGridVerbs(ids, this.sortOrder);
  }


  viewCurrentVerb(verb: Verb, verbModal: any){
    this.currentViewVerbId = verb.verbId;
    this.modalService.open(verbModal, this.modalOptions)

  }

}


