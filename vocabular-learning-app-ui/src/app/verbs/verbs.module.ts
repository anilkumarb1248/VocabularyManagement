import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VerbsListComponent } from './verbs-list/verbs-list.component';
import { VerbsRoutingModule } from './verbs-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AgGridModule } from 'ag-grid-angular';
import { ViewComponent } from './view/view.component';



@NgModule({
  declarations: [
    VerbsListComponent,
    ViewComponent
  ],
  imports: [
    CommonModule,
    VerbsRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    AgGridModule
  ]
})
export class VerbsModule { }
