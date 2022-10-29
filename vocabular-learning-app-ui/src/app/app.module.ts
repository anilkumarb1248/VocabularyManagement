import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule} from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AgGridModule } from 'ag-grid-angular';

import { AppComponent } from './app.component';
import { AgGridSampleComponent } from './ag-grid/ag-grid-sample/ag-grid-sample.component';
import { HeaderComponent } from './commons/header/header.component';
import { PageNotFoundComponent } from './commons/page-not-found/page-not-found.component';
import { TextToSpeechComponent } from './demos/text-to-speech/text-to-speech.component';
// import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    AgGridSampleComponent,
    HeaderComponent,
    PageNotFoundComponent,
    TextToSpeechComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AgGridModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule
    // NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
