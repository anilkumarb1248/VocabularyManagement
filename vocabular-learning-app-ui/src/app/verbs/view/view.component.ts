import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Verb } from 'src/app/models/verb';
import { VerbService } from 'src/app/services/verb.service';
import { ImageService } from 'src/app/services/image.service';
import { Speaker } from 'src/app/Voice/speaker';
import { Image } from 'src/app/models/image';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss']
})
export class ViewComponent implements OnInit {

  @Input()
  verbData: Verb | undefined;

  verbForm: any;
  selectedFile: File | undefined;
  retrievedImage: any;
  imageResponse:any;

  constructor(
    private verbService: VerbService,
    private speaker: Speaker,
    private imageService : ImageService) {

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
      learningStatus: new FormControl(this.verbData?.learningStatus),
      createdTimeStamp: new FormControl(this.verbData?.createdTimeStamp),
      updatedTimeStamp: new FormControl(this.verbData?.updatedTimeStamp)
    });

    this.loadImage(this.verbData?.verbId, "");

  }

  updateVerb(): void {
    let form = this.verbForm.value;

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

  

  onFileChanged(event:any){
    this.selectedFile = event.target.files[0];
  }

  onUpload(verbId:any){
    console.log("flasfldskjf: " + verbId)
    if(this.selectedFile && verbId){
      const uploadImageData = new FormData();
      uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

      this.imageService.uploadImage(uploadImageData, verbId).subscribe(
        (data) => {
          console.log(data.getStatus);
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
    console.log("****");
    this.imageService.getImage(verbId, imageName).subscribe(
      data => {
        if(data){
          this.imageResponse = data;
          let base64Data = this.imageResponse.image;
          // this.retrievedImage = 'data:image/jpeg;base64,' + base64Data;
          this.retrievedImage = 'data:'+this.imageResponse.type+';base64,' + base64Data;
        }
      }
    );
  }

  deleteImage(verbId:any){
    this.imageService.deleteImage(verbId).subscribe(
      data => {
        if(data){
          console.log(data.status);
        }
      }
    );
  }



}
