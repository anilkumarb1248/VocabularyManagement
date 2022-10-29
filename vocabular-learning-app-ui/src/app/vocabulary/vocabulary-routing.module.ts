import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from '../commons/page-not-found/page-not-found.component';
import { VocabularyHomeComponent } from './vocabulary-home/vocabulary-home.component';



const routes: Routes = [
  {
    path: '', children: [
      { path: "", redirectTo: "vocabulary-home", pathMatch: "full" },
      { path: "vocabulary-home", component: VocabularyHomeComponent }

    ]
  },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VocabularyRoutingModule { }
