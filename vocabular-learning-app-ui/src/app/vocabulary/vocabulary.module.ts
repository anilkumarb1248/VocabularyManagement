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
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { VocabularyHeadersComponent } from './vocabulary-headers/vocabulary-headers.component';

import {MatSidenavModule} from '@angular/material/sidenav'
import {MatGridListModule} from '@angular/material/grid-list';
import {MatTreeModule} from '@angular/material/tree';
import {MatIconModule} from '@angular/material/icon';


@NgModule({
  declarations: [
    VocabularyHomeComponent,
    VocabularyListComponent,
    VocabularyViewComponent,
    AllWordsComponent,
    AddWordsComponent,
    ViewWordComponent,
    VocabularyHeadersComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    VocabularyRoutingModule,
    CKEditorModule,

    MatSidenavModule,
    MatGridListModule,
    MatTreeModule,
    MatIconModule
  ]
})
export class VocabularyModule { }
