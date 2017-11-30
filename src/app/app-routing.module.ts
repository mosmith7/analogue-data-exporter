import { NgModule } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';
import {ReportOverviewComponent} from './report-overview/report-overview.component';
import {ReportDetailComponent} from "./report-detail/report-detail.component";
import {AppComponent} from "./app.component";

const routes: Routes = [
  { path: '', redirectTo: '/', pathMatch: 'full' },
  {path: '', component: AppComponent},
  {path: 'reports', component: ReportOverviewComponent},
  {path: 'reports/:id', component: ReportDetailComponent}
];

@NgModule({
  exports: [
    RouterModule
  ],
  imports: [ RouterModule.forRoot(routes)]
})
export class AppRoutingModule { }
