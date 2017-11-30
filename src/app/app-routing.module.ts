import { NgModule } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';
import {ReportOverviewComponent} from './report-overview/report-overview.component';

const routes: Routes = [
  { path: '', redirectTo: '/overview', pathMatch: 'full' },
  {path: 'overview', component: ReportOverviewComponent}
];

@NgModule({
  exports: [
    RouterModule
  ],
  imports: [ RouterModule.forRoot(routes)]
})
export class AppRoutingModule { }
