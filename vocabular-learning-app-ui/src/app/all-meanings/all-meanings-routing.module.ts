import { NgModule } from '@angular/core';
import { PageNotFoundComponent } from '../commons/page-not-found/page-not-found.component';
import { Routes, RouterModule } from '@angular/router';
import { AllMeaningsHomeComponent } from './all-meanings-home/all-meanings-home.component';
import { AllMeaningsListComponent } from './all-meanings-list/all-meanings-list.component';
import { AllMeaningsViewComponent } from './all-meanings-view/all-meanings-view.component';


const routes: Routes = [
  {
    path: '', children: [
      { path: "", redirectTo: "all-meanings-home", pathMatch: "full" },
      { path: "all-meanings-home", component: AllMeaningsHomeComponent }

    ]
  },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AllMeaningsRoutingModule { }
