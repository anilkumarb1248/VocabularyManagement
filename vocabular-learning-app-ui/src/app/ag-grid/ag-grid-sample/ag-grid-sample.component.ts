import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AgGridAngular } from 'ag-grid-angular';
import { CellClickedEvent, ColDef, GridReadyEvent } from 'ag-grid-community';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-ag-grid-sample',
  templateUrl: './ag-grid-sample.component.html',
  styleUrls: ['./ag-grid-sample.component.scss']
})
export class AgGridSampleComponent implements OnInit {
  //https://www.ag-grid.com/angular-data-grid/getting-started/

  ngOnInit(): void {
  }

  constructor(){

  }
  
  columnDefs = [
    {headerName: 'Make', field: 'make', sortable: true, filter: true },
    {headerName: 'Model', field: 'model', sortable: true, filter: true },
    {headerName: 'Price', field: 'price', sortable: true, filter: true}
];
rowData = [
    { make: 'Toyota', model: 'Celica', price: 35000 },
    { make: 'Ford', model: 'Mondeo', price: 32000 },
    { make: 'Porsche', model: 'Boxter', price: 72000 }
];
}
