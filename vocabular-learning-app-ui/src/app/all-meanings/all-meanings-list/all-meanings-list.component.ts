import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-all-meanings-list',
  templateUrl: './all-meanings-list.component.html',
  styleUrls: ['./all-meanings-list.component.scss']
})
export class AllMeaningsListComponent implements OnInit {

  @Input()
  currentMeaningType:string = "NEWS";

  constructor() { }

  ngOnInit(): void {
  }

}
