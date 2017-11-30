import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService }  from './in-memory-data.service';

import { AppComponent } from './app.component';
import { ReportOverviewComponent } from './report-overview/report-overview.component';
import {ReportsService} from './reports.service';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from "@angular/forms";
import {ReportDetailComponent} from "./report-detail/report-detail.component";


@NgModule({
  declarations: [
    AppComponent,
    ReportOverviewComponent,
    ReportDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    HttpClientInMemoryWebApiModule.forRoot(
      InMemoryDataService, { dataEncapsulation: false }
    )
  ],
  providers: [ReportsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
