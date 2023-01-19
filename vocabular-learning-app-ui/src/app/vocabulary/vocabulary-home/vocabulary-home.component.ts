import { Component, OnInit } from '@angular/core';
import { ListResponse } from 'src/app/models/list-response';
import { VocabularyHeader } from 'src/app/models/vocabulary-header';
import { VocabularHeaderService } from 'src/app/services/vocabular-header.service';

@Component({
  selector: 'app-vocabulary-home',
  templateUrl: './vocabulary-home.component.html',
  styleUrls: ['./vocabulary-home.component.scss']
})
export class VocabularyHomeComponent implements OnInit {

  loadAllWordsFlag: boolean = true;
  currentSelectedHeader: VocabularyHeader = new VocabularyHeader();


  constructor(
    private vocabularHeaderService: VocabularHeaderService
  ) {}

  ngOnInit(): void {
    
  }



  onAddHeaderBtnClick(): void {

  }

  onLoadAllWordsBtnClick(): void {
    console.log("load all words");
    this.loadAllWordsFlag = true;
  }


  onHeaderItemClicked(header: VocabularyHeader): void {
    this.loadAllWordsFlag = false;
    this.currentSelectedHeader = header;

  }

}
