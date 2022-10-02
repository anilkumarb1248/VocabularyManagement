import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from '../commons/page-not-found/page-not-found.component';
import { VerbsListComponent } from './verbs-list/verbs-list.component';



const routes: Routes = [
  {
    path: '', children: [
      { path: "", redirectTo: "verbsList", pathMatch: "full" },
      { path: "verbsList", component: VerbsListComponent }
      // { path: "add", component: AddComponent},
      // { path: "view/:employeeId", component: ViewComponent },
      // { path: "edit/:employeeId", component: EditComponent },
      // { path: '**', component:ListComponent}
    ]
  },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VerbsRoutingModule { }
