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
      this.words.push(new Word());
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
    if(newWords.length > 0){
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
    }else{
      console.log("Please add some words");
    }
  }

  closePopup() {
    this.wordWindowModal.dismiss();
  }

}
