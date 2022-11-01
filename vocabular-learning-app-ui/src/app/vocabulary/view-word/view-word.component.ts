import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, FormArray } from '@angular/forms';
import { Word } from 'src/app/models/word';
import { WordService } from 'src/app/services/word.service';

@Component({
  selector: 'app-view-word',
  templateUrl: './view-word.component.html',
  styleUrls: ['./view-word.component.scss']
})
export class ViewWordComponent implements OnInit {

  @Input()
  currentWord: Word = new Word();

  @Input()
  wordWindowModal: any | undefined;

  @Output() 
  wordGridListRefreshEvent = new EventEmitter();

  currentWordForm:FormGroup = new FormGroup({});

  constructor(private wordService: WordService, private fromBuilder: FormBuilder) {}

  ngOnInit(): void {
      this.currentWordForm = new FormGroup({
        wordId: new FormControl(this.currentWord.wordId),
        word: new FormControl(this.currentWord.word),
        phonetics:new FormControl(this.currentWord.phonetics),
        synonyms:new FormControl(this.currentWord.synonyms),
        antonyms:new FormControl(this.currentWord.antonyms),
        notes:new FormControl(this.currentWord.notes),
        meaning: new FormControl(this.currentWord.meaning)
      });
  }

  updateWord() {
    this.wordService.insertWord(this.currentWordForm.value).subscribe(
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
