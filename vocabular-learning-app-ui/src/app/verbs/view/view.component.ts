import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Verb } from 'src/app/models/verb';
import { VerbService } from 'src/app/services/verb.service';
import { ImageService } from 'src/app/services/image.service';
import { Speaker } from 'src/app/Voice/speaker';
import { Image } from 'src/app/models/image';
import { IndividualResponse } from 'src/app/models/individual-response';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss']
})
export class ViewComponent implements OnInit {

  @Input()
  verbId: number = 0;

  verbData: Verb | undefined;

  individualResponse : IndividualResponse | undefined;

  @Input()
  verbWindowModal:any | undefined;

  verbForm: any;
  selectedFile: File | undefined;
  retrievedImage: any;
  imageResponse:any;
  isLoaded: boolean = false;

  constructor(
    private verbService: VerbService,
    private speaker: Speaker,
    private imageService : ImageService) {
  
  }

  ngOnInit(): void {
    this.loadVerbDetails();
  }

  loadVerbDetails(){
    this.verbService.getVerbDetails(this.verbId).subscribe(
      (data) => {
        this.individualResponse = data;
        this.verbData = this.individualResponse.value;
        this.createVerbForm();
      },
      (error) => {
        console.log(error);
      }
    );
    this.loadImage(this.verbId, "");
  }

  createVerbForm(){
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
      learningStatus: new FormControl(this.verbData?.learningStatus),
      createdTimeStamp: new FormControl(this.verbData?.createdTimeStamp),
      updatedTimeStamp: new FormControl(this.verbData?.updatedTimeStamp)
    });
    this.isLoaded = true;
  }

  updateVerb(): void {
    let form = this.verbForm.value;
    if(form.baseForm){
      let updatedVerb: Verb = new Verb(
        form.verbId,
        form.baseForm,
        form.pastTenseForm,
        form.pastParticipleForm,
        form.thirdPersonBaseForm,
        form.progressiveForm,
        form.phonetics,
        this.splitText(form.meanings),
        this.splitText(form.examples),
        form.learningStatus,
        form.createdTimeStamp,
        form.updatedTimeStamp
      );
  
      this.verbService.updateVerb(updatedVerb).subscribe(
        (data) => {
          console.log(data.getStatus);
          this.closePopup();
        },
        (error) => {
          console.log(error);
          // this.verbWindowModal.dismiss('Cross click');
        }
      );
    }else{
      console.log("Base Form should not be null");
    }
  }

  closePopup(){
    this.verbWindowModal.dismiss();
  }

  stopSpeaking():void{
    this.speaker.stopSpeaking();
  }

  public speakVerbData():void{
    let verb = this.verbForm.value;
    let text = verb.baseForm + ". " + verb.pastTenseForm + ". " + verb.pastParticipleForm + ". " + verb.thirdPersonBaseForm + ". " + verb.progressiveForm + ". ";
    text = text + ".\n " + this.prepareMeaningsOrExamplesText("meanings");
    text = text + ".\n" + this.prepareMeaningsOrExamplesText("examples");

    this.speaker.speak(text);
  }

  prepareMeaningsOrExamplesText(type:string):string{
    let text = "";
    if (type == 'meanings') {
      text = "Meanings.\n";
      text = text + this.verbForm.value.meanings;
    } else if (type == 'examples') {
      text = "Examples.\n"
      text = text + this.verbForm.value.examples;
    }
    return text;
  }

  public textToSpeak(type: string): void {
    let text = this.prepareMeaningsOrExamplesText(type);
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

  onFileChanged(event:any){
    this.selectedFile = event.target.files[0];
    console.log(this.selectedFile);
  }

  onUpload(verbId:any){
    if(this.selectedFile && verbId){
      const uploadImageData = new FormData();
      uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

      this.imageService.uploadImage(uploadImageData, verbId).subscribe(
        (data) => {
          console.log(data.getStatus);
          this.loadImage(verbId, "");
        },
        (error) => {
          console.log(error);
        }
      );
    }else{
      console.log("Please upload an image");
    }
  }

  loadImage(verbId:any, imageName:string){
    this.imageService.getImage(verbId, imageName).subscribe(
      data => {
        if(data){
          this.imageResponse = data;
          let base64Data = this.imageResponse.image;
          this.retrievedImage = 'data:'+this.imageResponse.type+';base64,' + base64Data;
        }
      }
    );
  }

  deleteImage(verbId:any){
    this.imageService.deleteImage(verbId).subscribe(
      data => {
        if(data){
          this.imageResponse = null;
          console.log(data.status);
        }
      }
    );
  }



}
