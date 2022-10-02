import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AgGridAngular } from 'ag-grid-angular';
import { ListResponse } from 'src/app/models/list-response';
import { Verb } from 'src/app/models/verb';
import { VerbService } from 'src/app/services/verb.service';
import { Speaker } from 'src/app/Voice/speaker';

@Component({
  selector: 'app-verbs-list',
  templateUrl: './verbs-list.component.html',
  styleUrls: ['./verbs-list.component.scss']
})
export class VerbsListComponent implements OnInit {

  listResponse: ListResponse | undefined;
  verbs: Verb[] = new Array();
  isLoaded: boolean = false;

  @ViewChild('verbAgGrid') agGrid: AgGridAngular | undefined;

  columnDefs = [
    { headerName: 'Base Form', field: 'baseForm', type: 'string', sortable: true, filter: true, width: 200},
    { headerName: 'Past Tense Form', field: 'pastTenseForm', type: 'nonEditableColumn', sortable: false, filter: true, width: 200 },
    { headerName: 'Past Participle Form', field: 'pastParticipleForm', type: 'nonEditableColumn', sortable: false, filter: true, width: 200 },
    { headerName: 'Third Person Base Form', field: 'thirdPersonBaseForm', type: 'nonEditableColumn', sortable: false, filter: true, width: 200 },
    { headerName: 'Progressive Form', field: 'progressiveForm', type: 'nonEditableColumn', sortable: false, filter: true, width: 200 },
    { headerName: 'Phonetics', field: 'phonetics', type: 'nonEditableColumn', sortable: false, filter: true, width: 175 },
    { headerName: 'Action', field: 'actions', type: 'nonEditableColumn', sortable: false, filter: true, width: 140 }
  ];

  constructor(
    private verbService: VerbService,
    private router: Router,
    private speaker:Speaker
  ) { }

  ngOnInit(): void {
    this.loadEmployeesWithoutPagination();
    //You can select the voice using the indexex of getVoices()[1]
		// let voice = (speechSynthesis.getVoices()[3] || null);
    // this.speaker.setVoice(voice);
  }

  geClass(index: number): string {
    if (index % 2 == 0) {
      return "table-info";
    } else {
      return "table-warning";
    }
  }


  loadEmployeesWithoutPagination() {
    this.verbService.getVerbsList().subscribe(
      (data) => {
        console.log(data);
        this.listResponse = data;
        this.verbs = this.listResponse.values;
        this.isLoaded = true;
      },
      (error) => {
        console.log(error);
        this.isLoaded = true;
      }
    );
  }

  onRowClicked(event: any) {
    console.log(event.data);
    this.speaker.speak(event.data.baseForm);
    // $("#imagemodal").modal("show");
  }

  public textToSpeak(text:string):void {
    this.speaker.speak(text);
  }

  

  addInitialData() {
    throw new Error('Method not implemented.');
    }

}
