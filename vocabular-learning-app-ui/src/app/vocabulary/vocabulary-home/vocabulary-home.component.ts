import { Component, OnInit } from '@angular/core';
import { VocabularyHeader } from 'src/app/models/vocabulary-header';
import { VocabularHeaderService } from 'src/app/services/vocabular-header.service';

@Component({
  selector: 'app-vocabulary-home',
  templateUrl: './vocabulary-home.component.html',
  styleUrls: ['./vocabulary-home.component.scss']
})
export class VocabularyHomeComponent implements OnInit {

  headers: VocabularyHeader[] = new Array();
  loadAllWordsFlag:boolean = true;

  constructor(
    private vocabularHeaderService: VocabularHeaderService
  ) { 
  
    let a = new VocabularyHeader();
    a.vocabularyHeaderId = 1;
    a.vocabularyHeader = "News"
    this.headers.push(a);

    let b = new VocabularyHeader();
    b.vocabularyHeaderId = 2;
    b.vocabularyHeader = "Articles"
    this.headers.push(b);

    for(let i=10;i<=100;i++){
      let b = new VocabularyHeader();
      b.vocabularyHeaderId = i;
      b.vocabularyHeader = "AAAAAA_"+i
      this.headers.push(b);
    }

  }

  ngOnInit(): void {
  }

  onAddHeaderBtnClick():void{
    
  }

  onLoadAllWordsBtnClick():void{
     console.log("load all words");
     this.loadAllWordsFlag = true;
  }

  onHeaderItemClicked(header:VocabularyHeader):void{
    this.loadAllWordsFlag = false;

  }

}
