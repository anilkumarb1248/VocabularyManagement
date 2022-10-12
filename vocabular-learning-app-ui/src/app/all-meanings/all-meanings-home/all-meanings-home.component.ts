import { Component, OnInit } from '@angular/core';
import { MeaningType } from 'src/app/models/meaning-type';

@Component({
  selector: 'app-all-meanings-home',
  templateUrl: './all-meanings-home.component.html',
  styleUrls: ['./all-meanings-home.component.scss']
})
export class AllMeaningsHomeComponent implements OnInit {

  public meantingTypeEnum = MeaningType;
  currentMeaningType:string = "NEWS";

  constructor() { }

  ngOnInit(): void {
    this.onMeaningTypeChange(MeaningType.NEWS);
  }

  onMeaningTypeChange(meaningType: MeaningType):void{
    this.currentMeaningType = this.meantingTypeEnum[meaningType];
  }

}
