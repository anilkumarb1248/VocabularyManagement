import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AgGridAngular } from 'ag-grid-angular';
import { ListResponse } from 'src/app/models/list-response';
import { Verb } from 'src/app/models/verb';
import { VerbService } from 'src/app/services/verb.service';
import { Speaker } from 'src/app/Voice/speaker';

@Component({
  selector: 'app-verbs-list',
  templateUrl: './verbs-list.component.html',
  styleUrls: ['./verbs-list.component.scss']
})
export class VerbsListComponent implements OnInit {

  listResponse: ListResponse | undefined;
  verbs: Verb[] = new Array();
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
  showSpeakAllBtn:boolean = true;

  constructor(
    private verbService: VerbService,
    private speaker: Speaker
  ) { }

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
    this.verbService.getVerbsList(this.searchType, this.searchInput, this.sortOrder, this.selectedLetter, this.recordsSize).subscribe(
      (data) => {
        console.log(data);
        this.listResponse = data;
        this.verbs = this.listResponse.values;
        this.totalRecords = this.verbs.length;
        this.isLoaded = true;
      },
      (error) => {
        console.log(error);
        this.isLoaded = true;
      }
    );
  }

  public textToSpeak(text: string): void {
    this.speaker.speak(text);
  }

  modifySearh() {
    this.loadVerbsList();
  }

  speakAll()  {
    let preparedData = "";
    for (let verb of this.verbs) {
      preparedData = preparedData + this.prepareString(verb) + "\n";
    }
    this.showSpeakAllBtn = false;
    this.textToSpeak(preparedData);

  }

  stopSpeaking(){
    this.showSpeakAllBtn = true;
    this.speaker.stopSpeaking();
  }

  prepareString(verb:Verb):string{
    let data = verb.baseForm + ". " + verb.pastTenseForm + ". " + verb.pastParticipleForm + ". " + verb.thirdPersonBaseForm + ". " + verb.progressiveForm + ". ";


    return data;
  }
}
