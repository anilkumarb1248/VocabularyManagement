import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Verb } from 'src/app/models/verb';
import { VerbService } from 'src/app/services/verb.service';
import { Speaker } from 'src/app/Voice/speaker';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss']
})
export class ViewComponent implements OnInit {

  @Input()
  verbData: Verb | undefined;

  verbForm: any;

  constructor(
    private verbService: VerbService,
    private speaker: Speaker) {

  }

  ngOnInit(): void {
    console.log(this.verbData);

    this.verbForm = new FormGroup({
      verbId: new FormControl(this.verbData?.verbId),
      baseForm: new FormControl(this.verbData?.baseForm),
      pastTenseForm: new FormControl(this.verbData?.pastTenseForm),
      pastParticipleForm: new FormControl(this.verbData?.pastParticipleForm),
      thirdPersonBaseForm: new FormControl(this.verbData?.thirdPersonBaseForm),
      progressiveForm: new FormControl(this.verbData?.progressiveForm),
      phonetics: new FormControl(this.verbData?.phonetics),
      meanings: new FormControl(this.combineText(this.verbData?.meanings)),
      examples: new FormControl(this.combineText(this.verbData?.examples)),
      baseFormExamples: new FormControl(this.combineText(this.verbData?.baseFormExamples)),
      pastTenseExample: new FormControl(this.combineText(this.verbData?.pastTenseExample)),
      pastParticipleFormExample: new FormControl(this.combineText(this.verbData?.pastParticipleFormExample)),
      createdTimeStamp: new FormControl(this.verbData?.createdTimeStamp),
      updatedTimeStamp: new FormControl(this.verbData?.updatedTimeStamp)
    });
  }

  updateVerb(): void {

    let updatedVerb: Verb = new Verb(
      this.verbForm.value.verbId,
      this.verbForm.value.baseForm,
      this.verbForm.value.pastTenseForm,
      this.verbForm.value.pastParticipleForm,
      this.verbForm.value.thirdPersonBaseForm,
      this.verbForm.value.progressiveForm,
      this.verbForm.value.phonetics,
      this.splitText(this.verbForm.value.meanings),
      this.splitText(this.verbForm.value.examples),
      this.splitText(this.verbForm.value.baseFormExamples),
      this.splitText(this.verbForm.value.pastTenseExample),
      this.splitText(this.verbForm.value.pastParticipleFormExample),
      this.verbForm.value.createdTimeStamp,
      this.verbForm.value.updatedTimeStamp
    );
    console.log("Update here" + updatedVerb);

    this.verbService.updateVerb(updatedVerb).subscribe(
      (data) => {
        console.log(data.getStatus);
      },
      (error) => {
        console.log(error);
      }
    );

  }

  public textToSpeak(type: string): void {
    let text;
    if (type == 'meanings') {
      text = this.verbForm.value.meanings;
    } else if (type == 'examples') {
      text = this.verbForm.value.examples;
    } else if (type == 'baseFormExamples') {
      text = this.verbForm.value.baseFormExamples;
    } else if (type == 'pastTenseExample') {
      text = this.verbForm.value.pastTenseExample;
    } else if (type == 'pastParticipleFormExample') {
      text = this.verbForm.value.pastParticipleFormExample;
    }

    if (text) {
      this.speaker.speak(text);
    } else {
      this.speaker.speak("Please give me some text! so that I can speak");
    }

  }

  splitText(text: string): any[] {
    if(text){
      return text.split("\n");
    }else{
      return [];
    }
  }

  combineText(arrayStrings: any[] | undefined):string {
    let combinedText = "";
    if (arrayStrings) {
      for (let text of arrayStrings) {
          combinedText = combinedText + text + "\n";
      }
    }
    return combinedText;
  }

}
