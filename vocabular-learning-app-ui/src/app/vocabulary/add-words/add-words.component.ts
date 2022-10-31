import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Word } from 'src/app/models/word';
import { WordService } from 'src/app/services/word.service';

@Component({
  selector: 'app-add-words',
  templateUrl: './add-words.component.html',
  styleUrls: ['./add-words.component.scss']
})
export class AddWordsComponent implements OnInit {

  words: Word[] = new Array();

  @Input()
  wordWindowModal: any | undefined;

  @Output() wordGridListRefreshEvent = new EventEmitter();

  


  constructor(private wordService: WordService) {
    this.adEmptyRecords(5);
  }

  ngOnInit(): void {
  }

  adEmptyRecords(count:number) {
    for (let i = 1; i <= count; i++) {
      let emptyWord : Word = new Word();
      emptyWord.wordMeanings[0] = i + "";
      emptyWord.wordMeanings[1] = i + i + "";
      this.words.push(emptyWord);

    }
  }

  geClass(index: number): string {
    if (index % 2 == 0) {
      return "table-info";
    } else {
      return "table-warning";
    }
  }

  saveWordsList() {
    let newWords: Word[] = new Array();
    for(let word of this.words){
      if(word.word){
        newWords.push(word);
      }
    }
    this.wordService.insertWordsList(newWords).subscribe(
      (data) => {
        console.log(data.getStatus);
        this.closePopup();
        this.wordGridListRefreshEvent.emit();
      },
      (error) => {
        console.log(error);
      }
    );

  }

  closePopup() {
    this.wordWindowModal.dismiss();
  }

}
