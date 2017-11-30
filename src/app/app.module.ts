import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { ReportOverviewComponent } from './report-overview/report-overview.component';
import {ReportsService} from './reports.service';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';


@NgModule({
  declarations: [
    AppComponent,
    ReportOverviewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [ReportsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
