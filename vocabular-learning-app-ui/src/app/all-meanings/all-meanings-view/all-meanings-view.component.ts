import { Component, OnInit } from '@angular/core';
import { MeaningType } from 'src/app/models/meaning-type';

@Component({
  selector: 'app-all-meanings-view',
  templateUrl: './all-meanings-view.component.html',
  styleUrls: ['./all-meanings-view.component.scss']
})
export class AllMeaningsViewComponent implements OnInit {

  type : MeaningType =  MeaningType.NEWS;
  public meantingTypeEnum = MeaningType;
  currentMeaningType:string = "NEWS";

  constructor() { 
    this.currentMeaningType = this.meantingTypeEnum[this.type];

   }

  ngOnInit(): void {
  }

}
