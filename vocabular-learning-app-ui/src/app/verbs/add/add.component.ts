import { Component, Input, OnInit } from '@angular/core';
import { LearningStatus } from 'src/app/models/learning-status';
import { Verb } from 'src/app/models/verb';
import { VerbService } from 'src/app/services/verb.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {

  verbs: Verb[] = new Array();

  @Input()
  verbWindowModal: any | undefined;

  emptyVerb : Verb = new Verb(0,"","","","","","",
  [],[],LearningStatus.NOT_STARTED,new Date(),new Date());


  constructor(private verbService: VerbService) {
    this.adEmptyRecords(5);
  }

  ngOnInit(): void {
  }

  adEmptyRecords(count:number) {
    for (let i = 1; i <= count; i++) {
      let v: Verb = new Verb(0,"","","","","","",
        [],[],LearningStatus.NOT_STARTED,new Date(),new Date());
      this.verbs.push(v);

    }
  }

  geClass(index: number): string {
    if (index % 2 == 0) {
      return "table-info";
    } else {
      return "table-warning";
    }
  }

  saveVerbs() {
    let newVerbs: Verb[] = new Array();
    for(let verb of this.verbs){
      if(verb.baseForm){
        verb.meanings = this.splitText(verb.meanings);
        verb.examples = this.splitText(verb.examples);
        newVerbs.push(verb);
      }
    }
    this.verbService.saveVerbs(newVerbs).subscribe(
      (data) => {
        console.log(data.getStatus);
        this.closePopup();
      },
      (error) => {
        console.log(error);
      }
    );

  }

  splitText(text: any): string[] {
    console.log(text);
    if(text && !Array.isArray(text)){
      return text.split("\n");
    }else{
      return [];
    }
  }

  closePopup() {
    this.verbWindowModal.dismiss();
  }

}
