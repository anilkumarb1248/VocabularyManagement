import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgGridSampleComponent } from './ag-grid/ag-grid-sample/ag-grid-sample.component';
import { PageNotFoundComponent } from './commons/page-not-found/page-not-found.component';
import { TextToSpeechComponent } from './demos/text-to-speech/text-to-speech.component';

const routes: Routes = [
  { path: '', redirectTo: "agGridSample", pathMatch: "full" },
  // { path:'home', component: HomeComponent},
  { path:'agGridSample', component: AgGridSampleComponent},
  { path:'textToSpeech', component: TextToSpeechComponent},
  { path: 'verbs', loadChildren: () => import('./verbs/verbs.module').then(m => m.VerbsModule)},
  { path: 'vocabulary', loadChildren: () => import('./vocabulary/vocabulary.module').then(m => m.VocabularyModule)},
  // { path: 'user', loadChildren: () => import('./user/user.module').then(m => m.UserModule), canActivate:[UserGuard]},
  // { path: 'employee', loadChildren: () => import('./employee/employee.module').then(m => m.EmployeeModule),canActivate:[UserGuard] },
  // { path: 'topics', loadChildren: () => import('./topics/topics.module').then(m => m.TopicsModule) },
  { path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
