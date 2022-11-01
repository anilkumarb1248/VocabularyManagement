import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VocabularyRoutingModule } from './vocabulary-routing.module';
import { VocabularyHomeComponent } from './vocabulary-home/vocabulary-home.component';
import { VocabularyListComponent } from './vocabulary-list/vocabulary-list.component';
import { VocabularyViewComponent } from './vocabulary-view/vocabulary-view.component';
import { AllWordsComponent } from './all-words/all-words.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddWordsComponent } from './add-words/add-words.component';
import { ViewWordComponent } from './view-word/view-word.component';



@NgModule({
  declarations: [
    VocabularyHomeComponent,
    VocabularyListComponent,
    VocabularyViewComponent,
    AllWordsComponent,
    AddWordsComponent,
    ViewWordComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    VocabularyRoutingModule
  ]
})
export class VocabularyModule { }
