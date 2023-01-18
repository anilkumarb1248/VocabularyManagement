import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AllMeaningsRoutingModule } from './all-meanings-routing.module';
import { AllMeaningsListComponent } from './all-meanings-list/all-meanings-list.component';
import { AllMeaningsViewComponent } from './all-meanings-view/all-meanings-view.component';
import { AllMeaningsHomeComponent } from './all-meanings-home/all-meanings-home.component';



@NgModule({
  declarations: [
    AllMeaningsListComponent,
    AllMeaningsViewComponent,
    AllMeaningsHomeComponent
  ],
  imports: [
    CommonModule,
    AllMeaningsRoutingModule
  ]
})
export class AllMeaningsModule { }
